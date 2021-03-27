package greader.view;

import greader.view.renderer.ClosableTabbedPane;

import java.awt.event.ActionListener;

public interface ParentComponent {

    ClosableTabbedPane getMainPanel();

    ActionListener getOpenAction();

    ActionListener getMergeAction();

    ActionListener getSplitAction();

}
