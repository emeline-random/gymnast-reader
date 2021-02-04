package greader.view.tabview;

import org.icepdf.ri.common.ComponentKeyBinding;
import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;
import org.icepdf.ri.util.ViewerPropertiesManager;
import greader.model.LoadData;

import javax.swing.*;

public class PdfTabView {

    public static JPanel getView(String filepath) {
        SwingController controller = new SwingController();
        ViewerPropertiesManager manager = ViewerPropertiesManager.getInstance();
        manager.set("application.lookandfeel", "com.formdev.flatlaf.FlatDarculaLaf");
        SwingViewBuilder factory = new SwingViewBuilder(controller);
        JPanel viewerComponentPanel = factory.buildViewerPanel();
        ComponentKeyBinding.install(controller, viewerComponentPanel);
        controller.getDocumentViewController().setAnnotationCallback(
                new org.icepdf.ri.common.MyAnnotationCallback(
                        controller.getDocumentViewController()));
        controller.openDocument(filepath);
        LoadData.addFile(filepath);
        return viewerComponentPanel;
    }

}
