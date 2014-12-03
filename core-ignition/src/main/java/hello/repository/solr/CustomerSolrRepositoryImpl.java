package hello.repository.solr;

import hello.solr.CustomerSolr;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class CustomerSolrRepositoryImpl implements CustomCustomerSolrRepository {

	@Autowired
	@Qualifier("solrCustomerTemplate")
	SolrTemplate solrTemplate;

	@Override
	public List<CustomerSolr> search(String searchTerm) {
		log.debug("Building a criteria query with search term: {}", searchTerm);

		String[] words = searchTerm.split(" ");

		Criteria conditions = createSearchConditions(words);
		SimpleQuery search = new SimpleQuery(conditions);
		search.addSort(sortByIdDesc());

		Page results = solrTemplate.queryForPage(search, CustomerSolr.class);
		return results.getContent();
	}

	@Override
	public void update(CustomerSolr customerSolr) {
		// TODO Auto-generated method stub
	}

	private Criteria createSearchConditions(String[] words) {
		Criteria conditions = null;
		for (String word : words) {
			if (conditions == null) {
				conditions = new Criteria("text").contains(word);

			} else {
				conditions = conditions.or(new Criteria("text").contains(word));
			}
		}
		return conditions;
	}

	private Sort sortByIdDesc() {
		return new Sort(Sort.Direction.DESC, "first_name");
	}
}
