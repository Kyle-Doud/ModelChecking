package experimentDesign;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.stream.*;
import javax.xml.transform.dom.*;



public class ToXML {
	  
	  StreamResult out;
	  Document xmldoc;
	  Element root;
	  Element e0;
	  String str;
	  String first = "";
	  String last = "";
	  int step = 0;
	  int flag = 0;
	  String []nodeName ={"percentLikeNeighbors","initialNoOfAgents","worldWidth","minDeathAge", "maxDeathAge","worldHeight"};

	  
	  
	  public void doit (Factor factor) {
		    try{
		     // in = new BufferedReader(new FileReader("C:/Users/sritika/Documents/Research Project/Development/runtime-EclipseXtext/ExperimentOntology/src/data.xml"));
		      out = new StreamResult("C:/Users/sritika/Documents/Fall 2015/MDE/ReferenceImpl/ReferenceImplementation1/src/batch_params.xml");
		      initXML();
		      
		      e0 = xmldoc.createElement("sweep");
		      xmldoc.appendChild(e0);
		      Attr e0Attr = xmldoc.createAttribute("runs");
		      e0Attr.setValue("1");
			  e0.setAttributeNode(e0Attr); 
		      
		      
		      
		         process(factor);
		     
		     // in.close();
		      
		       }
		    catch (Exception e) { e.printStackTrace(); }
		  }
	  
	  public void initXML() throws ParserConfigurationException{
		    
		    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		    DocumentBuilder builder = factory.newDocumentBuilder();
		    DOMImplementation impl = builder.getDOMImplementation();

		    //xmldoc = impl.createDocument(null, "Schelling_Model", null);
		    xmldoc = builder.newDocument();
		    root = xmldoc.getDocumentElement();
		  }
	  
	  public void process(Factor factor) {
		  try{
			  for(int j=0; j<nodeName.length;j++){
				  String nodeValue = factorValue(factor);
				  if(factor.getFactorName().equals(nodeName[j])){
						  Element e1 = xmldoc.createElement("parameter");
						  e0.appendChild(e1);
						  Attr e1Attr1 =xmldoc.createAttribute("valueRange");
						  e1Attr1.setNodeValue(nodeValue);
						  e1.setAttributeNode(e1Attr1);
						  Attr e1Attr2 =xmldoc.createAttribute("type");
						  e1Attr2.setNodeValue("constant");
						  e1.setAttributeNode(e1Attr2);
						  Attr e1Attr3 =xmldoc.createAttribute("constant_type");
						  e1Attr3.setNodeValue("double");
						  e1.setAttributeNode(e1Attr3);
						  Attr e1Attr4 =xmldoc.createAttribute("name");
						  e1Attr4.setNodeValue(nodeName[j]);
						  e1.setAttributeNode(e1Attr4);
			 
	  
		  
				  	} 
			  	}
			  writeXML();
		  }
			  
		  
		  catch(Exception e){
			  e.printStackTrace();
		  }
		  
		  
		  }

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
	  
	  public void writeXML() throws TransformerConfigurationException,TransformerException {
		  DOMSource domSource = new DOMSource(xmldoc);
		  TransformerFactory tf = TransformerFactory.newInstance();
		  tf.setAttribute("indent-number", 2);
		  Transformer transformer = tf.newTransformer();
		  transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		  transformer.transform(domSource, out);
		   
}}
