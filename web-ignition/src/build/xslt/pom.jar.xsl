<?xml version="1.0"?>
<!-- Transforms a pom that builds WARs to a pom that builds JARs -->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:pom="http://maven.apache.org/POM/4.0.0">
	<xsl:output method="xml" encoding="UTF-16" omit-xml-declaration="yes" standalone="yes" indent="yes" />
	<xsl:template match="/">
		<xsl:comment>
			This is generated from pom.xml. DO NOT EDIT OR SAVE IN VERSION CONTROL
		</xsl:comment>
		<xsl:apply-templates />
	</xsl:template>
	<xsl:template match="pom:project/pom:packaging/text()">
		<xsl:text>jar</xsl:text>
	</xsl:template>
	<xsl:template match="pom:project/pom:dependencies/pom:dependency/pom:scope[../pom:artifactId[text()='spring-boot-starter-tomcat']]" />
	<xsl:template match="pom:project/pom:build/pom:plugins/pom:plugin[pom:artifactId[text()='maven-war-plugin']]" />
	<xsl:template match="pom:project/pom:build/pom:plugins/pom:plugin[pom:artifactId[text()='jetty-maven-plugin']]" />
	<xsl:template match="pom:project/pom:build/pom:plugins/pom:plugin[pom:artifactId[text()='xml-maven-plugin']]" />
	<xsl:template match="pom:project/pom:pluginManagement/pom:plugins/pom:plugin[pom:artifactId[text()='lifecycle-mapping']]" />
	<xsl:template match="@*|*|processing-instruction()|comment()">
		<xsl:copy>
			<xsl:apply-templates select="*|@*|text()|processing-instruction()|comment()" />
		</xsl:copy>
	</xsl:template>
</xsl:stylesheet>