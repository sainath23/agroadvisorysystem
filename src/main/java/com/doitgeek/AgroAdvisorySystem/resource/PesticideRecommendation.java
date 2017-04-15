package com.doitgeek.AgroAdvisorySystem.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
//import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.RDFNode;
//import org.apache.jena.rdf.model.ModelFactory;
//import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.riot.RDFDataMgr;

import com.doitgeek.AgroAdvisorySystem.model.Utility;

@Path("/pesticides")
public class PesticideRecommendation {

	@GET
	@Path("/result")
	@Produces(MediaType.APPLICATION_JSON)
	public String predictionBasedOnPartAndSymptom(@QueryParam("pest_name") String pest_name) {
		String response = "";
		Model model = RDFDataMgr.loadModel("http://myontology.16mb.com/ontology/CottonOntology.owl");
		//Model model = ModelFactory.createDefaultModel();
		//model.read("http://myontology.16mb.com/ontology/CottonOntology.owl");
		String queryString = 
						"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"+
						"PREFIX owl: <http://www.w3.org/2002/07/owl#>"+
						"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"+
						"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"+
						"PREFIX co: <http://www.semanticweb.org/sainath/ontologies/2016/11/CottonOntology#>"+
						"select ?Insect ?Symptoms ?Prevention where{?Insect co:hasDataSymptom ?Symptoms."+
													"?Insect co:isPreventedBy ?Prevention."+
													"?Insect co:hasName co:"+pest_name+".}";
		String insectName = "";
		String symptoms = "";
		String pesticides = "";
		Query query = QueryFactory.create(queryString);
		QueryExecution qexec = QueryExecutionFactory.create(query, model);
		try {
			ResultSet results = qexec.execSelect();
			if(results.hasNext() == true) 
				while(results.hasNext()) {
					QuerySolution soln = results.nextSolution();
					RDFNode insect = soln.get("Insect");
					insectName = insect.asNode().getLocalName();
					symptoms = soln.get("Symptoms").toString();
					pesticides = soln.get("Prevention").toString();
				}		
			else {
				insectName = "No disease found!";
				symptoms = "Pesticides not found!";
				pesticides = "Pesticides not found!";
			}
			response = Utility.constructJSONForSPARQL(new String[] 
									{insectName, symptoms, pesticides});
		} finally {
			qexec.close();
		}
		
		return response;
	}
}
