package bean;

import java.io.IOException;
import java.io.InputStream;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.Part;

@ManagedBean
@ViewScoped
public class UploadBean2 {
    private Part file;

    public void upload() throws IOException {
        try ( InputStream input = file.getInputStream() ) {
            // FileUtils.copyToFile( input, new File(
            // "/home/john/Pictures/uploads/test.jpg" ) );
        }
    }

    public Part getFile() {
        return file;
    }

    public void setFile( Part file ) {
        this.file = file;
    }

}
