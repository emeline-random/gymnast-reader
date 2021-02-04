package greader.utils;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class PdfUtils {

    public static Document mergePdfs(ArrayList<File> filesToMerge, String path) throws IOException, DocumentException {
        Document document = new com.itextpdf.text.Document();
        PdfCopy copy = new PdfCopy(document, new FileOutputStream(path.endsWith(".pdf") ? path : path + ".pdf"));
        document.setPageSize(PageSize.A4);
        document.open();
        for (File f : filesToMerge) {
            PdfReader reader = new PdfReader(new FileInputStream(f));
            copy.addDocument(reader);
            copy.freeReader(reader);
            reader.close();
        }
        document.close();
        return document;
    }

    // Start index = 1 !!!
    public static void splitPdf(File pdf, int from, int to, String dest) throws IOException, DocumentException {
        String inFile = pdf.getAbsolutePath();
        PdfReader reader = new PdfReader(new FileInputStream(inFile));
        if (to < from || from <= 0 || to > reader.getNumberOfPages()) {
            throw new IllegalArgumentException("Check your indexes ! (should be from 1 to " + reader.getNumberOfPages() + ")");
        }
        Document document = new com.itextpdf.text.Document();
        PdfCopy copy = new PdfCopy(document, new FileOutputStream(dest.endsWith(".pdf") ? dest : dest + ".pdf"));
        document.setPageSize(PageSize.A4);
        document.open();
        for (int i = from; i <= to; i++) copy.addPage(copy.getImportedPage(reader, i));
        copy.freeReader(reader);
        reader.close();
        document.close();
    }

}
