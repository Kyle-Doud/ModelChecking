package experimentDesign;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * 
 * Stores the experiment design specification in parameters.xml
 * which is used to run RePast project.
 *
 */
public class ToParametersXML {

	  
	  StreamResult out;
	  Document xmldoc;
	  Element root;
	  Element e0;
	  String str;
	  String first = "";
	  String last = "";
	  int step = 0;
	  int flag = 0;
	  String []nodeName ={"percentLikeNeighbors","initialNumAgents","worldWidth","minDeathAge", "maxDeathAge","worldHeight"};
	  String []nodeDisplayName ={"Percent Like Neighbors","Initial Num Agents","World Width","Min Death Age", "Max Death Age","World Height"};

	  /**
	   * It stores the factors in the parameters.xml
	   * @param factor The factor input specified for the experiment run
	   */
	  
	  public void doit (Factor factor) {
		    try{
		     // in = new BufferedReader(new FileReader("C:/Users/sritika/Documents/Research Project/Development/runtime-EclipseXtext/ExperimentOntology/src/data.xml"));
		      out = new StreamResult("C:/Users/sritika/Documents/Fall 2015/MDE/ReferenceImpl/ReferenceImplementation1/src/parameters1.xml");
		      initXML();
		      
		      e0 = xmldoc.createElement("parameters");
		      xmldoc.appendChild(e0);
		      //Attr e0Attr = xmldoc.createAttribute("runs");
		      //e0Attr.setValue("1");
			  //e0.setAttributeNode(e0Attr); 
		      
		      
		      
		         process(factor);
		     
		     // in.close();
		      
		       }
		    catch (Exception e) { e.printStackTrace(); }
		  }
	  /**
	   * Initializes the xml file
	   * @throws ParserConfigurationException when there is a serious error in the parsing configuration
	   */
	  
	  public void initXML() throws ParserConfigurationException{
		    
		    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		    DocumentBuilder builder = factory.newDocumentBuilder();
		    DOMImplementation impl = builder.getDOMImplementation();

		    //xmldoc = impl.createDocument(null, "Schelling_Model", null);
		    xmldoc = builder.newDocument();
		    root = xmldoc.getDocumentElement();
		  }
	  
	  /**
	   * Creates the nodes and stores the factor in it
	   * @param factor The factor input specified for the experiment run
	   */
	  
	  public void process(Factor factor) {
		  try{
			  for(int j=0; j<nodeName.length;j++){
				  String nodeValue = factorValue(factor);
				  if(factor.getFactorName().equals(nodeName[j])){
						  Element e1 = xmldoc.createElement("parameter");
						  e0.appendChild(e1);
						  Attr e1Attr1 =xmldoc.createAttribute("name");
						  e1Attr1.setNodeValue(nodeName[j]);
						  e1.setAttributeNode(e1Attr1);
						  Attr e1Attr2 =xmldoc.createAttribute("displayName");
						  e1Attr2.setNodeValue(nodeDisplayName[j]);
						  e1.setAttributeNode(e1Attr2);
						  Attr e1Attr3 =xmldoc.createAttribute("type");
						  e1Attr3.setNodeValue("int");
						  e1.setAttributeNode(e1Attr3);
						  Attr e1Attr4 =xmldoc.createAttribute("defaultValue");
						  e1Attr4.setNodeValue(nodeValue);
						  e1.setAttributeNode(e1Attr4);
						  Attr e1Attr5 =xmldoc.createAttribute("isReadOnly");
						  e1Attr5.setNodeValue("false");
						  e1.setAttributeNode(e1Attr5);
						  Attr e1Attr6 =xmldoc.createAttribute("converter");
						  e1Attr6.setNodeValue("repast.simphony.parameter.StringConverterFactory$IntConverter");
						  e1.setAttributeNode(e1Attr6);
	  
		  
				  	} 
			  	}
			  writeXML();
		  }
			  
		  
		  catch(Exception e){
			  e.printStackTrace();
		  }
		  
		  
		  }
	  /**
	   * Extracts the factor value and returns it in String format
	   * @param factor The factor input specified for the experiment run
	   * @return returns the factor value as a String
	   */

	private String factorValue(Factor factor) {
		Values v = factor.getFactorValues();
		 String nodeValue = null;
		 String type = v.getValueType();
		 if(type.equals("String")){
			nodeValue = v.getFunction();
		 }
		 else if(type.equals("Array")){
			String []factorValue = v.getFactorLevel();
			for(int i = 0; i< factorValue.length;i++){
				nodeValue = nodeValue + factorValue[i];
			}
			nodeValue.trim();
		 }
		 else if(type.equals("Integer")){
				nodeValue = Integer.toString(v.getI());
			 }
		 else if(type.equals("Double")){
				nodeValue = Double.toString(v.getF());
			 }
		 else if(type.equals("Character")){
				nodeValue = String.valueOf(v.getS());
			 }
		 else{
			 Boolean b = v.isB();
			 if(b) 
				 nodeValue = "true";
			 else
				 nodeValue = "false";
		 }
		return nodeValue;
	}
	 
	/**
	 * Writes the data in the XML file
	 * @throws TransformerConfigurationException When there is a serious configuration error.
	 * @throws TransformerException When there is an exceptional condition that occurred during the transformation process
	 */
	  public void writeXML() throws TransformerConfigurationException,TransformerException {
		  DOMSource domSource = new DOMSource(xmldoc);
		  TransformerFactory tf = TransformerFactory.newInstance();
		  tf.setAttribute("indent-number", 2);
		  Transformer transformer = tf.newTransformer();
		  transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		  transformer.transform(domSource, out);
		   
}

}
