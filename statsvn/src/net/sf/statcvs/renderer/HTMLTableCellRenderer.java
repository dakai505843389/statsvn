/*
	StatCvs - CVS statistics generation 
	Copyright (C) 2002  Lukasz Pekacki <lukasz@pekacki.de>
	http://statcvs.sf.net/
    
	This library is free software; you can redistribute it and/or
	modify it under the terms of the GNU Lesser General Public
	License as published by the Free Software Foundation; either
	version 2.1 of the License, or (at your option) any later version.

	This library is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
	Lesser General Public License for more details.

	You should have received a copy of the GNU Lesser General Public
	License along with this library; if not, write to the Free Software
	Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
    
	$RCSfile: HTMLTableCellRenderer.java,v $
	$Date: 2003/12/09 00:42:54 $
*/
package net.sf.statcvs.renderer;

import net.sf.statcvs.model.Author;
import net.sf.statcvs.model.VersionedFile;
import net.sf.statcvs.model.Directory;
import net.sf.statcvs.output.HTMLOutput;
import net.sf.statcvs.output.HTMLTagger;
import net.sf.statcvs.output.OutputRenderer;


/**
 * Helper class for rendering different types of table cells and table heads
 * to HTML
 * 
 * @author Richard Cyganiak <rcyg@gmx.de>
 * @version $Id: HTMLTableCellRenderer.java,v 1.2 2003/12/09 00:42:54 cyganiak Exp $
 */
public class HTMLTableCellRenderer implements TableCellRenderer {

	private String html = null;
    
    private OutputRenderer output = null;

	/**
	 * Render a generic table cell to HTML
	 * @param content the cell's content
	 */
	public void renderCell(final String content) {
		html = content;
	}
	
	/**
	 * Render an empty cell to HTML
	 */
	public void renderEmptyCell() {
		html = null;
	}

	/**
	 * Render an integer cell to HTML
	 * @param value the cell's content
	 */
	public void renderIntegerCell(final int value) {
		html = Integer.toString(value);
	}

	/**
	 * Render an integer cell to HTML, showing both the integer value and
	 * a percentage of a total
	 * @param value the cell's content
	 * @param total the total, worth 100%
	 */
	public void renderIntegerCell(final int value, final int total) {
		html = Integer.toString(value) + " ("
				+ getPercentage((double) value / (double) total) + ")";
	}

	/**
	 * Render a percentage cell to HTML
	 * @param ratio the cell's content
	 */
	public void renderPercentageCell(final double ratio) {
		html = getPercentage(ratio);
	}

	/**
	 * Render a cell containing an author to HTML
	 * @param author the author
	 */
	public void renderAuthorCell(final Author author) {
		html = HTMLTagger.getAuthorLink(author, output);
	}

	/**
	 * Render a cell containing a directory to HTML
	 * @param directory the directory
	 */
	public void renderDirectoryCell(final Directory directory) {
		html = HTMLTagger.getDirectoryLink(directory, getOutput());
	}

	/**
	 * Render a cell containing a file to HTML
	 * @param file the file
	 * @param withIcon display an icon in front of the filename?
	 */
	public void renderFileCell(final VersionedFile file, final boolean withIcon) {
		html = HTMLTagger.getFileLink(file);
		if (withIcon) {
			if (file.isDead()) {
				html = HTMLTagger.getIcon(HTMLOutput.DELETED_FILE_ICON) + " " + html;
			} else {
				html = HTMLTagger.getIcon(HTMLOutput.FILE_ICON) + " " + html;
			}
		}
	}

	/**
	 * Return the results of the last <tt>renderCell</tt> call
	 * @return HTML
	 */
	public String getColumnHead() {
		return getHtml("th");
	}

	/**
	 * Return the results of the last <tt>renderCell</tt> call
	 * as a row head
	 * @return HTML
	 */
	public String getRowHead() {
		return getHtml("th");
	}

	/**
	 * Return the results of the last <tt>renderCell</tt> call
	 * as an ordinary table cell
	 * @return HTML
	 */
	public String getTableCell() {
		return getHtml("td");
	}
	
	private String getPercentage(final double ratio) {
		if (Double.isNaN(ratio)) {
			return "-";
		}
		final int percentTimes10 = (int) Math.round(ratio * 1000);
		final double percent = percentTimes10 / 10.0;
		return Double.toString(percent) + "%";
	}
	
	private String getHtml(final String tag) {
		if (html == null) {
			return "<" + tag + "></" + tag + ">";
		}
		return "<" + tag + ">" + html + "</" + tag + ">";
	}

    /**
     * @return the output
     */
    public OutputRenderer getOutput() {
        return output;
    }

    /**
     * @param output the output to set
     */
    public void setOutput(final OutputRenderer output) {
        this.output = output;
    }
    
    public String getOddRowFormat() {
        return output.getOddRowFormat();
    }
    
    public String getEvenRowFormat() {
        return output.getEvenRowFormat();
    }
}