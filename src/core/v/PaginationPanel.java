/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core.v;

/**
 *
 * @author tobikster
 */
public class PaginationPanel extends javax.swing.JPanel {
  private int _currentPage;
	private int _pageCount;
	private ButtonsListener _listener;
	
	public interface ButtonsListener {
		public void nextButtonClicked();
		public void previousButtonClicked();
		public void firstButtonClicked();
		public void lastButtonClicked();
	}
	
	/**
	 * Creates new form PaginationPanel
	 */
	public PaginationPanel() {
		_currentPage = 0;
		_pageCount = 0;
		_listener = new ButtonsListener() {

			@Override
			public void nextButtonClicked() {
			}

			@Override
			public void previousButtonClicked() {
			}

			@Override
			public void firstButtonClicked() {
			}

			@Override
			public void lastButtonClicked() {
			}
		};
		initComponents();
		update();
	}
	
	public void setCurrentPage(int currentPage) {
		_currentPage = currentPage;
		update();
	}
	
	public void setPageCount(int pageCount) {
		_pageCount = pageCount;
		update();
	}
	
	public void setButtonsListener(ButtonsListener listener){
		_listener = listener;
	}
	
	public void update() {
		_gotoFirstPageButton.setEnabled(true);
		_gotoPreviousPageButton.setEnabled(true);
		_gotoNextPageButton.setEnabled(true);
		_gotoLastPageButton.setEnabled(true);
		if (_currentPage <= 0) {
			_gotoPreviousPageButton.setEnabled(false);
			_gotoFirstPageButton.setEnabled(false);
		}
		if (_currentPage >= _pageCount - 1) {
			_gotoNextPageButton.setEnabled(false);
			_gotoLastPageButton.setEnabled(false);
		}
		_currentPagelabel.setText((_currentPage + 1) + "/" + _pageCount);
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        _gotoFirstPageButton = new javax.swing.JButton();
        _gotoPreviousPageButton = new javax.swing.JButton();
        _currentPagelabel = new javax.swing.JLabel();
        _gotoNextPageButton = new javax.swing.JButton();
        _gotoLastPageButton = new javax.swing.JButton();

        _gotoFirstPageButton.setText("<<");
        _gotoFirstPageButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _gotoFirstPageButtonActionPerformed(evt);
            }
        });
        add(_gotoFirstPageButton);

        _gotoPreviousPageButton.setText("<");
        _gotoPreviousPageButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _gotoPreviousPageButtonActionPerformed(evt);
            }
        });
        add(_gotoPreviousPageButton);
        add(_currentPagelabel);

        _gotoNextPageButton.setText(">");
        _gotoNextPageButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _gotoNextPageButtonActionPerformed(evt);
            }
        });
        add(_gotoNextPageButton);

        _gotoLastPageButton.setText(">>");
        _gotoLastPageButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _gotoLastPageButtonActionPerformed(evt);
            }
        });
        add(_gotoLastPageButton);
    }// </editor-fold>//GEN-END:initComponents

    private void _gotoFirstPageButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__gotoFirstPageButtonActionPerformed
		_listener.firstButtonClicked();
    }//GEN-LAST:event__gotoFirstPageButtonActionPerformed

    private void _gotoPreviousPageButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__gotoPreviousPageButtonActionPerformed
		_listener.previousButtonClicked();
    }//GEN-LAST:event__gotoPreviousPageButtonActionPerformed

    private void _gotoNextPageButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__gotoNextPageButtonActionPerformed
		_listener.nextButtonClicked();
    }//GEN-LAST:event__gotoNextPageButtonActionPerformed

    private void _gotoLastPageButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__gotoLastPageButtonActionPerformed
		_listener.lastButtonClicked();
    }//GEN-LAST:event__gotoLastPageButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel _currentPagelabel;
    private javax.swing.JButton _gotoFirstPageButton;
    private javax.swing.JButton _gotoLastPageButton;
    private javax.swing.JButton _gotoNextPageButton;
    private javax.swing.JButton _gotoPreviousPageButton;
    // End of variables declaration//GEN-END:variables
}