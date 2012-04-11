/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imagen;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import org.dcm4che2.data.DicomElement;
import org.dcm4che2.data.DicomObject;
import org.dcm4che2.io.DicomInputStream;
import org.dcm4che2.util.TagUtils;

/**
 *
 * @author juansrs
 */


public class DicomImg {
    
    //Atributos de clase
    private DicomObject dcmObj;
    
    public DicomImg(String rutaArchivo) {
        DicomInputStream din = null;
        try {
            din = new DicomInputStream(new File(rutaArchivo));
            dcmObj = din.readDicomObject();
        }
        catch (IOException e) {
            e.printStackTrace();
            return;
        }
        finally {
            try {
                din.close();
            }
            catch (IOException ignore) {
            }
        }
    }
    
    public void listDicomHeader(DicomObject dcmObj) {
        Iterator<DicomElement> iter = dcmObj.datasetIterator();
        while(iter.hasNext()) {
            DicomElement element = iter.next();
            int tag = element.tag();
            try {
                String tagName = dcmObj.nameOf(tag);
                String tagAddr = TagUtils.toString(tag);
                String tagVR = dcmObj.vrOf(tag).toString();
                if (tagVR.equals("SQ")) {
                    if (element.hasItems()) {
                        System.out.println(tagAddr +" ["+  tagVR +"] "+ tagName);
                        listDicomHeader(element.getDicomObject());
                        continue;
                    }
                }
                String tagValue = dcmObj.getString(tag);
                System.out.println(tagAddr +" ["+ tagVR +"] "+ tagName +" ["+ tagValue+"]");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public DicomObject getDicomObject() {
        return dcmObj;
    }
}
