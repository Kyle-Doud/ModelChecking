package experimentDesign;
import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 * 
 * It modifies the file sink in RePast to update the output file location
 *
 */
public class FileSinkModifier {
	
	/**
	 * 
	 * It modifies the file sink in RePast to update the output file location
	 * 
	 */
	public void startModifier(String output){
	try{
		String filepath = "C:/Users/sritika/Documents/Research Project/Development/RepastSimphony/Schelling/Schelling.rs/repast.simphony.action.file_sink_2.xml";
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document doc = docBuilder.parse(filepath);
		
		// Get the root element
		Node company = doc.getFirstChild();
		
		Node staff = doc.getElementsByTagName("fileName").item(0);
		staff.setTextContent(output);
		
		
		
		// write the content into xml file
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File(filepath));
		transformer.transform(source, result);
		
		System.out.println("Done");
		
		} 
		catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} 
		catch (TransformerException tfe) {
			tfe.printStackTrace();
		} 
		catch (IOException ioe) {
			ioe.printStackTrace();
		} 
		catch (SAXException sae) {
			sae.printStackTrace();
		}
}

}
