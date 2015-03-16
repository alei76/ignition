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
	protected SolrTemplate solrTemplate;

	private Criteria createSearchConditions(final String[] words) {
		Criteria conditions = null;
		for (final String word : words) {
			if (conditions == null) {
				conditions = new Criteria("text").contains(word);

			} else {
				conditions = conditions.or(new Criteria("text").contains(word));
			}
		}
		return conditions;
	}

	@Override
	public List<CustomerSolr> search(final String searchTerm) {
		log.debug("Building a criteria query with search term: {}", searchTerm);

		final String[] words = searchTerm.split(" ");

		final Criteria conditions = createSearchConditions(words);
		final SimpleQuery search = new SimpleQuery(conditions);
		search.addSort(sortByIdDesc());

		final Page results = solrTemplate.queryForPage(search, CustomerSolr.class);
		return results.getContent();
	}

	private Sort sortByIdDesc() {
		return new Sort(Sort.Direction.DESC, "first_name");
	}

	@Override
	public void update(final CustomerSolr customerSolr) {
		// TODO Auto-generated method stub
	}
}
