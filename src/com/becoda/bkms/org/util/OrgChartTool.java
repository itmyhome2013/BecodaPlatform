package com.becoda.bkms.org.util;


import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.org.pojo.bo.OrgBO;
import com.becoda.bkms.util.FileUtil;

import java.util.List;

/**
 * List list = new ArrayList();
 * List tm1 = new ArrayList();
 * tm1.add("1");
 * list.add(tm1);
 * //             list.add(new ArrayList());
 * for(int i=0;i<3;i++){
 * List tm = new ArrayList();
 * for(int j=0;j<(i+1)*3;j++){
 * tm.add(""+j);
 * }
 * list.add(tm);
 * }
 * OrgChartTool tool = new OrgChartTool();
 * tool.drawOrgChart(list);
 * Created by IntelliJ IDEA.
 * User: lrg
 * Date: 2015-7-24
 * Time: 21:54:36
 * To change this template use File | Settings | File Templates.
 */
public class OrgChartTool {
    //  private int layer=0;
    private int id = 10000;
    //  private ListMap hashElements = new ListMap();
    //private int bodyWidth = 22800;   //画板总宽度
    private int leftMagin = 500;  //左边距
    private int topMagin = 1500;
//    private int verticalTextBoxWidth = 567;  //竖直文本宽度
    //    private int verticalTextBoxHeight = 2028; //竖直文本高度
    private int verticalTextBoxWidth = 260;  //竖直文本宽度
    private int verticalTextBoxHeight = 2718; //竖直文本高度
    private int HorizontalTextBoxWidth = 2659; //水平文本宽度
    private int HorizontalTextBoxHeight = 468; //水平文本高度

    private int verticalShortLineLength = 312;  //竖直短线长度

    private int spaceBetweenLength = 90;//文本框间距

    private final String xmlHeader = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
            "<?mso-application progid=\"Word.Document\"?>" +
            "<w:wordDocument xmlns:w=\"http://schemas.microsoft.com/office/word/2003/wordml\" xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:w10=\"urn:schemas-microsoft-com:office:word\" xmlns:sl=\"http://schemas.microsoft.com/schemaLibrary/2003/core\" xmlns:aml=\"http://schemas.microsoft.com/aml/2001/core\" xmlns:wx=\"http://schemas.microsoft.com/office/word/2003/auxHint\" xmlns:o=\"urn:schemas-microsoft-com:office:office\" xmlns:dt=\"uuid:C2F41010-65B3-11d1-A29F-00AA00C14882\" w:macrosPresent=\"no\" w:embeddedObjPresent=\"no\" w:ocxPresent=\"no\" xml:space=\"preserve\">" +
            "<o:DocumentProperties>" +
            "<o:Title>" +
            " </o:Title>" +
            "<o:Author>MC SYSTEM</o:Author>" +
            "<o:LastAuthor>MC SYSTEM</o:LastAuthor>" +
            "<o:Revision>1</o:Revision>" +
            "<o:TotalTime>50</o:TotalTime>" +
            "<o:Created>2015-7-24T14:53:00Z</o:Created>" +
            "<o:LastSaved>2015-7-24T15:43:00Z</o:LastSaved>" +
            "<o:Pages>1</o:Pages>" +
            "<o:Words>4</o:Words>" +
            "<o:Characters>25</o:Characters>" +
            "<o:Company>MC SYSTEM</o:Company>" +
            "<o:Lines>1</o:Lines>" +
            "<o:Paragraphs>1</o:Paragraphs>" +
            "<o:CharactersWithSpaces>28</o:CharactersWithSpaces>" +
            "<o:Version>11.5604</o:Version>" +
            "</o:DocumentProperties>" +
            "<w:fonts>" +
            "<w:defaultFonts w:ascii=\"Times New Roman\" w:fareast=\"宋体\" w:h-ansi=\"Times New Roman\" w:cs=\"Times New Roman\"/>" +
            "<w:font w:name=\"宋体\">" +
            "<w:altName w:val=\"SimSun\"/>" +
            "<w:panose-1 w:val=\"02010600030101010101\"/>" +
            "<w:charset w:val=\"86\"/>" +
            "<w:family w:val=\"Auto\"/>" +
            "<w:pitch w:val=\"variable\"/>" +
            "<w:sig w:usb-0=\"00000003\" w:usb-1=\"080E0000\" w:usb-2=\"00000010\" w:usb-3=\"00000000\" w:csb-0=\"00040001\" w:csb-1=\"00000000\"/>" +
            "</w:font>" +
            "<w:font w:name=\"@宋体\">" +
            "<w:panose-1 w:val=\"02010600030101010101\"/>" +
            "<w:charset w:val=\"86\"/>" +
            "<w:family w:val=\"Auto\"/>" +
            "<w:pitch w:val=\"variable\"/>" +
            "<w:sig w:usb-0=\"00000003\" w:usb-1=\"080E0000\" w:usb-2=\"00000010\" w:usb-3=\"00000000\" w:csb-0=\"00040001\" w:csb-1=\"00000000\"/>" +
            "</w:font>" +
            "</w:fonts>" +
            "<w:styles>" +
            "<w:versionOfBuiltInStylenames w:val=\"4\"/>" +
            "<w:latentStyles w:defLockedState=\"off\" w:latentStyleCount=\"156\"/>" +
            "<w:style w:type=\"paragraph\" w:default=\"on\" w:styleId=\"a\">" +
            "<w:name w:val=\"Normal\"/>" +
            "<wx:uiName wx:val=\"正文\"/>" +
            "<w:pPr>" +
            "<w:widowControl w:val=\"off\"/>" +
            "<w:jc w:val=\"both\"/>" +
            "</w:pPr>" +
            "<w:rPr>" +
            "<wx:font wx:val=\"Times New Roman\"/>" +
            "<w:kern w:val=\"2\"/>" +
            "<w:sz w:val=\"21\"/>" +
            "<w:sz-cs w:val=\"24\"/>" +
            "<w:lang w:val=\"EN-US\" w:fareast=\"ZH-CN\" w:bidi=\"AR-SA\"/>" +
            "</w:rPr>" +
            "</w:style>" +
            "<w:style w:type=\"character\" w:default=\"on\" w:styleId=\"a0\">" +
            "<w:name w:val=\"Default Paragraph Font\"/>" +
            "<wx:uiName wx:val=\"默认段落字体\"/>" +
            "<w:semiHidden/>" +
            "</w:style>" +
            "<w:style w:type=\"table\" w:default=\"on\" w:styleId=\"a1\">" +
            "<w:name w:val=\"Normal Table\"/>" +
            "<wx:uiName wx:val=\"普通表格\"/>" +
            "<w:semiHidden/>" +
            "<w:rPr>" +
            "<wx:font wx:val=\"Times New Roman\"/>" +
            "</w:rPr>" +
            "<w:tblPr>" +
            "<w:tblInd w:w=\"0\" w:type=\"dxa\"/>" +
            "<w:tblCellMar>" +
            "<w:top w:w=\"0\" w:type=\"dxa\"/>" +
            "<w:left w:w=\"108\" w:type=\"dxa\"/>" +
            "<w:bottom w:w=\"0\" w:type=\"dxa\"/>" +
            "<w:right w:w=\"108\" w:type=\"dxa\"/>" +
            "</w:tblCellMar>" +
            "</w:tblPr>" +
            "</w:style>" +
            "<w:style w:type=\"list\" w:default=\"on\" w:styleId=\"a2\">" +
            "<w:name w:val=\"No List\"/>" +
            "<wx:uiName wx:val=\"无列表\"/>" +
            "<w:semiHidden/>" +
            "</w:style>" +
            "</w:styles>" +
            "<w:shapeDefaults>" +
            "<o:shapedefaults v:ext=\"edit\" spidmax=\"2050\"/>" +
            "<o:shapelayout v:ext=\"edit\">" +
            "<o:idmap v:ext=\"edit\" data=\"1\"/>" +
            "</o:shapelayout>" +
            "</w:shapeDefaults>" +
            "<w:docPr>" +
            "<w:view w:val=\"print\"/>" +
            "<w:zoom w:percent=\"100\"/>" +
            "<w:doNotEmbedSystemFonts/>" +
            "<w:bordersDontSurroundHeader/>" +
            "<w:bordersDontSurroundFooter/>" +
            "<w:proofState w:spelling=\"clean\" w:grammar=\"clean\"/>" +
            "<w:attachedTemplate w:val=\"\"/>" +
            "<w:defaultTabStop w:val=\"420\"/>" +
            "<w:drawingGridVerticalSpacing w:val=\"156\"/>" +
            "<w:displayHorizontalDrawingGridEvery w:val=\"0\"/>" +
            "<w:displayVerticalDrawingGridEvery w:val=\"2\"/>" +
            "<w:punctuationKerning/>" +
            "<w:characterSpacingControl w:val=\"CompressPunctuation\"/>" +
            "<w:optimizeForBrowser/>" +
            "<w:validateAgainstSchema/>" +
            "<w:saveInvalidXML w:val=\"off\"/>" +
            "<w:ignoreMixedContent w:val=\"off\"/>" +
            "<w:alwaysShowPlaceholderText w:val=\"off\"/>" +
            "<w:compat>" +
            "<w:spaceForUL/>" +
            "<w:balanceSingleByteDoubleByteWidth/>" +
            "<w:doNotLeaveBackslashAlone/>" +
            "<w:ulTrailSpace/>" +
            "<w:doNotExpandShiftReturn/>" +
            "<w:adjustLineHeightInTable/>" +
            "<w:breakWrappedTables/>" +
            "<w:snapToGridInCell/>" +
            "<w:wrapTextWithPunct/>" +
            "<w:useAsianBreakRules/>" +
            "<w:dontGrowAutofit/>" +
            "<w:useFELayout/>" +
            "</w:compat>" +
            "</w:docPr>" +
            "<w:body>" +
            "<wx:sect>" +
            "<w:p>" +
            "<w:pPr>" +
            "<w:rPr>" +
            "<w:rFonts w:hint=\"fareast\"/>" +
            "</w:rPr>" +
            "</w:pPr>" +
            "<w:r>" +
            "<w:rPr>" +
            "<w:rFonts w:hint=\"fareast\"/>" +
            "<w:noProof/>" +
            "</w:rPr>" +
            "</w:r>" +
            "<w:r>" +
            "<w:pict>" +
            "<v:group id=\"_x0000_s1027\" editas=\"canvas\" style=\"width:15in;height:694.2pt;mso-position-horizontal-relative:char;mso-position-vertical-relative:line\" coordorigin=\"1134,1501\" coordsize=\"21600,13884\">" +
            "<o:lock v:ext=\"edit\" aspectratio=\"t\"/>" +
            "<v:shapetype id=\"_x0000_t75\" coordsize=\"21600,21600\" o:spt=\"75\" o:preferrelative=\"t\" path=\"m@4@5l@4@11@9@11@9@5xe\" filled=\"f\" stroked=\"f\">" +
            "<v:stroke joinstyle=\"miter\"/>" +
            "<v:formulas>" +
            "<v:f eqn=\"if lineDrawn pixelLineWidth 0\"/>" +
            "<v:f eqn=\"sum @0 1 0\"/>" +
            "<v:f eqn=\"sum 0 0 @1\"/>" +
            "<v:f eqn=\"prod @2 1 2\"/>" +
            "<v:f eqn=\"prod @3 21600 pixelWidth\"/>" +
            "<v:f eqn=\"prod @3 21600 pixelHeight\"/>" +
            "<v:f eqn=\"sum @0 0 1\"/>" +
            "<v:f eqn=\"prod @6 1 2\"/>" +
            "<v:f eqn=\"prod @7 21600 pixelWidth\"/>" +
            "<v:f eqn=\"sum @8 21600 0\"/>" +
            "<v:f eqn=\"prod @7 21600 pixelHeight\"/>" +
            "<v:f eqn=\"sum @10 21600 0\"/>" +
            "</v:formulas>" +
            "<v:path o:extrusionok=\"f\" gradientshapeok=\"t\" o:connecttype=\"rect\"/>" +
            "<o:lock v:ext=\"edit\" aspectratio=\"t\"/>" +
            "</v:shapetype>" +
            "<v:shape id=\"_x0000_s1026\" type=\"#_x0000_t75\" style=\"position:absolute;left:1134;top:1501;width:21600;height:13884\" o:preferrelative=\"f\">" +
            "<v:fill o:detectmouseclick=\"t\"/>" +
            "<v:path o:extrusionok=\"t\" o:connecttype=\"none\"/>" +
            "<o:lock v:ext=\"edit\" text=\"t\"/>" +
            "</v:shape>" +
            "<v:shapetype id=\"_x0000_t202\" coordsize=\"21600,21600\" o:spt=\"202\" path=\"m,l,21600r21600,l21600,xe\">" +
            "<v:stroke joinstyle=\"miter\"/>" +
            "<v:path gradientshapeok=\"t\" o:connecttype=\"rect\"/>" +
            "</v:shapetype>";
    private final String xmlEnd = "<w10:wrap type=\"none\"/>" +
            "<w10:anchorlock/>" +
            "</v:group>" +
            "</w:pict>" +
            "</w:r>" +
            "</w:p>" +
            "<w:sectPr>" +
            "<w:pgSz w:w=\"23814\" w:h=\"16840\" w:orient=\"landscape\" w:code=\"8\"/>" +
            "<w:pgMar w:top=\"1418\" w:right=\"1134\" w:bottom=\"1418\" w:left=\"1134\" w:header=\"851\" w:footer=\"992\" w:gutter=\"0\"/>" +
            "<w:cols w:space=\"425\"/>" +
            "<w:docGrid w:type=\"lines\" w:line-pitch=\"312\"/>" +
            "</w:sectPr>" +
            "  </wx:sect>" +
            "    </w:body>" +
            "</w:wordDocument>";

    private String createHorizontalTextBox(int x, int y, String value, boolean showInnerOrg) {
        String textBox = "<v:shape id=\"_x0000_s" + (id++) + "\" type=\"#_x0000_t202\" style=\"position:absolute;left:" + x + ";top:" + y + ";width:2659;height:468\">" +
                "<v:textbox inset=\"2.20981mm,1.1049mm,2.20981mm,1.1049mm\">" +
                "<w:txbxContent>" +
                "<w:p>" +
                "<w:pPr>" +
                "<w:jc w:val=\"center\"/>" +
                "<w:rPr>" +
                "<w:rFonts w:hint=\"fareast\"/>" +
                "<w:sz-cs w:val=\"21\"/>" +
                "</w:rPr>" +
                "</w:pPr>" +
                "<w:r>" +
                "<w:rPr>" +
                "<w:rFonts w:hint=\"fareast\"/>" +
                "<wx:font wx:val=\"宋体\"/>" +
                "<w:sz-cs w:val=\"21\"/>" +
                "</w:rPr>" +
                "<w:t>" + value + "</w:t>" +
                "</w:r>" +
                "</w:p>" +
                "</w:txbxContent>" +
                "</v:textbox>" +
                "</v:shape>";
        String line;
        if (showInnerOrg)
            line = createLine((x + this.HorizontalTextBoxWidth / 2), y + this.HorizontalTextBoxHeight, (x + this.HorizontalTextBoxWidth / 2), (y + this.HorizontalTextBoxHeight + this.verticalShortLineLength * 3 + this.verticalTextBoxHeight));
        else
            line = createLine((x + this.HorizontalTextBoxWidth / 2), y + this.HorizontalTextBoxHeight, (x + this.HorizontalTextBoxWidth / 2), (y + this.HorizontalTextBoxHeight + this.verticalShortLineLength));
        return line + textBox;
    }

    private String createVerticalTextBox(int x, int y, String value, boolean belowLine) {

//                textBox =
//                "<v:shape id=\"_x0000_s" + (id++) + "\" type=\"#_x0000_t202\" style=\"position:absolute;left:" + x + ";top:" + y + ";width:567;height:2028\">" +
//                        "<v:textbox style=\"layout-flow:vertical-ideographic\">" +
//                        "<w:txbxContent>" +
//                        "<w:p>" +
//                        "<w:pPr>" +
//                        "<w:jc w:val=\"center\"/>" +
//                        "<w:rPr>" +
//                        "<w:rFonts w:hint=\"fareast\"/>" +
//                        "</w:rPr>" +
//                        "</w:pPr>" +
//                        "<w:r>" +
//                        "<w:rPr>" +
//                        "<w:rFonts w:hint=\"fareast\"/>" +
//                        "<wx:font wx:val=\"宋体\"/>" +
//                        "</w:rPr>" +
//                        "<w:t>" + value + "</w:t>" +
//                        "</w:r>" +
//                        "</w:p>" +
//                        "<w:p>" +
//                        "<w:pPr>" +
//                        "<w:jc w:val=\"center\"/>" +
//                        "</w:pPr>" +
//                        "</w:p>" +
//                        "</w:txbxContent>" +
//                        "</v:textbox>" +
//                        "</v:shape>";

        String textBox = " <v:shape id=\"_x0000_s" + (id++) + "\"  type=\"#_x0000_t202\"\n" +
                "           style=\"position:absolute;left:" + x + ";top:" + y + ";width:260;height:2718\">\n" +
                "      <v:textbox style=\"layout-flow:vertical-ideographic\">\n" +
                "          <w:txbxContent>\n" +
                "              <w:p/>\n" +
                "          </w:txbxContent>\n" +
                "      </v:textbox>\n" +
                "  </v:shape>";
        textBox += "<v:shape id=\"_x0000_s" + (id++) + "\" type=\"#_x0000_t202\"\n" +
                "         style=\"position:absolute;left:" + (x + 130 - 283) + ";top:" + y + ";width:566;height:2854\" filled=\"f\"\n" +
                "         stroked=\"f\">\n" +
                "    <v:textbox style=\"layout-flow:vertical-ideographic\">\n" +
                "        <w:txbxContent>\n" +
                "            <w:p>\n" +
                "                <w:pPr>\n" +
                "                    <w:rPr>\n" +
                "                        <w:sz w:val=\"18\"/>\n" +
                "                        <w:sz-cs w:val=\"18\"/>\n" +
                "                    </w:rPr>\n" +
                "                </w:pPr>\n" +
                "                <w:r>\n" +
                "                    <w:rPr>\n" +
                "                        <w:rFonts w:hint=\"fareast\"/>\n" +
                "                        <wx:font wx:val=\"宋体\"/>\n" +
                "                        <w:sz w:val=\"18\"/>\n" +
                "                        <w:sz-cs w:val=\"18\"/>\n" +
                "                    </w:rPr>\n" +
                "                    <w:t>" + value + "</w:t>\n" +
                "                </w:r>\n" +
                "            </w:p>\n" +
                "            <w:p/>\n" +
                "        </w:txbxContent>\n" +
                "    </v:textbox>\n" +
                "</v:shape>";
        String line = createLine((x + this.verticalTextBoxWidth / 2), y, (x + this.verticalTextBoxWidth / 2), (y - this.verticalShortLineLength));
        if (belowLine) {
            line += createLine((x + this.verticalTextBoxWidth / 2), y + this.verticalTextBoxHeight, (x + this.verticalTextBoxWidth / 2), (y + this.verticalTextBoxHeight + this.verticalShortLineLength));
        }
        return line + textBox;
    }

    private String createLine(int fromX, int fromY, int toX, int toY) {
        return "<v:line id=\"_x0000_s" + (id++) + "\" style=\"position:absolute\" from=\"" + fromX + "," + fromY + "\" to=\"" + toX + "," + toY + "\"/>";
    }

//    //分析画图使用的最大宽度
//    private int analyzeUseMaxWidth(List list) {
//        if (list == null) return 0;
//        int maxCount = 0; //元素最大数
//        for (int i = 0; i < list.size(); i++) {
//            List tmpList = (List) list.get(i);
//            if (tmpList.size() > maxCount) maxCount = tmpList.size();
//        }
//        return maxCount * (verticalTextBoxWidth + this.spaceBetweenLength);
//    }

    //确定文本框的起始位置

    private int ComputeTextBoxBeginYPosition(OrgChartElement parent) {
        int x, y;
        if (parent == null) {
            //  x = this.leftMagin;
            y = this.topMagin;
        } else {
            //   x = parent.getX();
            y = parent.getY() + parent.getHeight() + this.verticalShortLineLength * 2;
        }
        //int i[] = new int[2];

        return y;
    }

    private void ComputeOrgElementsCoordinate(OrgChartElement parentElement, boolean showInnerOrg) {
        //  String content = "";
        if (parentElement == null) return;
        if (parentElement.getParent() == null) {
            parentElement.setX(this.leftMagin);
            parentElement.setY(this.topMagin);
            parentElement.setHeight(this.HorizontalTextBoxHeight);
            parentElement.setWidth(this.HorizontalTextBoxWidth);
        }
        if (parentElement.getChilds() == null) return;
        for (int i = 0; i < parentElement.getChilds().size(); i++) {
            OrgChartElement element = (OrgChartElement) parentElement.getChilds().get(i);
            int y = ComputeTextBoxBeginYPosition(parentElement);
            //如果要显示内设机构,并且上上级机构为空,代表是第二层,相应的纵坐标下移一条端线长度+竖直文本框告诉
            if (showInnerOrg && element.getParent().getParent() == null) {
                y = y + 2 * this.verticalShortLineLength + this.verticalTextBoxHeight;
            }
            element.setY(y);
            OrgChartElement frontEle = element.getFront();
            if (frontEle != null) {
                element.setX(frontEle.getX() + this.verticalTextBoxWidth + this.spaceBetweenLength);
            } else {
                element.setX(parentElement.getX());
            }
            element.setHeight(this.verticalTextBoxHeight);
            element.setWidth(this.verticalTextBoxWidth);
            if (i == parentElement.getChilds().size() - 1)
                adjustParentElementCoordinate(element);
        }
        for (int i = 0; i < parentElement.getChilds().size(); i++) {
            OrgChartElement element = (OrgChartElement) parentElement.getChilds().get(i);
            ComputeOrgElementsCoordinate(element, showInnerOrg);
        }


    }

    /**
     * 调整上级结点横坐标位置
     *
     * @param orgElement
     */
    private void adjustParentElementCoordinate(OrgChartElement orgElement) {
        if (orgElement.getFront() != null && orgElement.getNext() == null) {  //如果是最后一个元素，并且上级元素不为空，调整上级结点的位置
            //得到当前层的总宽度
            //   OrgChartElement firstElement;
            OrgChartElement firstElement = orgElement;
            while (firstElement.getFront() != null) {
                firstElement = firstElement.getFront();
            }

            int width = orgElement.getX() - firstElement.getX();
            OrgChartElement parent = orgElement.getParent();
            if (parent != null) {
                //中间点横坐标
                int middleXCoordinate = firstElement.getX() + firstElement.getWidth() / 2 + width / 2;
                int xCoordinate = middleXCoordinate - parent.getWidth() / 2; //计算所得父结点坐标
                if (xCoordinate > parent.getX()) { //如果计算所得父结点坐标大于父结点坐标
                    int addValue = xCoordinate - parent.getX();
                    parent.setX(xCoordinate);
                    adjustParentElementCoordinate(parent);
                    adjustBrotherNodeAddValue(parent, addValue * 2); //调整兄弟结点坐标 ,2倍增加值的宽度
                } else {  //如果小于父结点坐标，则移动当前节点以及兄弟节点 // 此时，一般出现在华第二层时，第二层节点太少的情况
                    int parentMiddleXCoordinate = parent.getX() + parent.getWidth() / 2;
                    OrgChartElement fElement = orgElement;
                    int cnt = 1;
                    while (fElement.getFront() != null) {
                        fElement = fElement.getFront();
                        cnt++;
                    }
                    int sumWidth = cnt * (this.verticalTextBoxWidth + this.spaceBetweenLength) - this.spaceBetweenLength;
                    int leftX = parentMiddleXCoordinate + sumWidth / 2 - this.verticalTextBoxWidth;
                    fElement = orgElement;
                    fElement.setX(leftX);
                    while (fElement.getFront() != null) {
                        leftX -= this.verticalTextBoxWidth + this.spaceBetweenLength;
                        fElement = fElement.getFront();
                        fElement.setX(leftX);
                    }
                    //adjustParentElementCoordinate(parent);
                }
            }
        } else {
            //如果是一个元素,则调整父结点
            if (orgElement.getFront() == null && orgElement.getNext() == null && orgElement.getParent() != null) {
                int xCoordinate = orgElement.getX() + orgElement.getWidth() / 2 - orgElement.getParent().getWidth() / 2; //计算所得父结点坐标
                if (xCoordinate > orgElement.getParent().getX()) { //如果计算所得父结点坐标大于父结点坐标 {
                    int addValue = xCoordinate - orgElement.getParent().getX();
                    orgElement.getParent().setX(xCoordinate);
                    adjustParentElementCoordinate(orgElement.getParent());
                    adjustBrotherNodeAddValue(orgElement.getParent(), addValue * 2); //调整兄弟结点坐标
                } else if (xCoordinate < orgElement.getParent().getX()) {
                    int parentMiddleXCoordinate = orgElement.getParent().getX() + orgElement.getParent().getWidth() / 2;
                    //OrgChartElement fElement = orgElement;
                    orgElement.setX(parentMiddleXCoordinate - orgElement.getWidth() / 2);

                }
            }
        }


    }

    //调整兄弟结点坐标
    private void adjustBrotherNodeAddValue(OrgChartElement element, int addValue) {
        if (element != null) {
            OrgChartElement ele = element;
            while (ele.getNext() != null) {
                ele = ele.getNext();
                ele.setX(ele.getX() + addValue);
                adjustChildNodeAddValue(ele, addValue);

            }
            if (!ele.equals(element))
                adjustParentElementCoordinate(ele);
        }
    }


    //调整字结点结点坐标
    private void adjustChildNodeAddValue(OrgChartElement element, int addValue) {
        if (element != null) {
            if (element.getChilds() != null) {
                for (int i = 0; i < element.getChilds().size(); i++) {
                    OrgChartElement ele = (OrgChartElement) element.getChilds().get(i);
                    ele.setX(ele.getX() + addValue);
                    adjustChildNodeAddValue(ele, addValue);
                }
            }
        }
    }

    /**
     * 创建内容
     *
     * @param rootElement
     */
    private String createOrgChartContent(OrgChartElement rootElement, boolean showInnerOrg) {
        String content;
        if (rootElement == null) return "";
        if (rootElement.getParent() == null) {
            content = this.createHorizontalTextBox(rootElement.getX(), rootElement.getY(), rootElement.getName(), showInnerOrg);
        } else {
            boolean blBelowLine = false;
            if (rootElement.getChilds() != null)
                blBelowLine = true;
            content = this.createVerticalTextBox(rootElement.getX(), rootElement.getY(), rootElement.getName(), blBelowLine);
        }
        if (rootElement.getChilds() != null) {
            for (int i = 0; i < rootElement.getChilds().size(); i++) {
                content += createOrgChartContent((OrgChartElement) rootElement.getChilds().get(i), showInnerOrg);
            }
        }
        return content;
    }

    /**
     * 画横线连接所有父结点相同的子元素
     *
     * @param orgElement
     */
    private String createOrgChartHorizontalLine(OrgChartElement orgElement) {
        List childs = orgElement.getChilds();
        String lines = "";
        if (childs == null) return "";
        if (childs.size() > 1) {
            OrgChartElement first = (OrgChartElement) childs.get(0);
            OrgChartElement last = (OrgChartElement) childs.get(childs.size() - 1);
            int fromx = first.getX() + first.getWidth() / 2;
            int fromy = first.getY() - this.verticalShortLineLength;
            int tox = last.getX() + last.getWidth() / 2;
            int toy = last.getY() - this.verticalShortLineLength;
            lines = this.createLine(fromx, fromy, tox, toy);
        }
        for (int i = 0; i < childs.size(); i++) {
            lines += createOrgChartHorizontalLine((OrgChartElement) childs.get(i));
        }
        return lines;
    }

    private String createInnerOrgChart(OrgChartElement rootElement, List innerOrgList) {
        String content = "";
        if (innerOrgList != null && innerOrgList.size() > 0) {
            int middlePosX = rootElement.getX() + rootElement.getWidth() / 2;
            int y = rootElement.getY() + rootElement.getHeight() + this.verticalShortLineLength * 2;
            int leftX = middlePosX - this.verticalTextBoxWidth / 2 - (innerOrgList.size() / 2 + 1) * (this.verticalTextBoxWidth + this.spaceBetweenLength);      //最左边位置
            int x = leftX;
            for (int i = 0; i < innerOrgList.size(); i++) {
                if (x >= middlePosX - this.verticalTextBoxWidth && x <= middlePosX + this.verticalTextBoxWidth) {
                    x += this.verticalTextBoxWidth * 2;
                }
                content += this.createVerticalTextBox(x, y, ((OrgBO) innerOrgList.get(i)).getName(), false);
                x += this.verticalTextBoxWidth + this.spaceBetweenLength;
            }

            //画横线
            y = y - this.verticalShortLineLength;
            if (x != leftX)
                content += this.createLine(leftX + this.verticalTextBoxWidth / 2, y, x - this.spaceBetweenLength - this.verticalTextBoxWidth / 2, y);
        }
        return content;
    }

    /**
     * @param absolutePath 绝对路径
     * @param rootElement  根节点元素
     * @param innerOrgList 要显示的内设机构列表，空，代表不显示
     * @return
     * @throws BkmsException
     */
    public String drawOrgChart(String absolutePath, OrgChartElement rootElement, List innerOrgList) throws BkmsException {
//        throw new RuntimeException("方法未实现");
        boolean showInnerOrg = false;
        if (innerOrgList != null && innerOrgList.size() > 0)
            showInnerOrg = true;
        ComputeOrgElementsCoordinate(rootElement, showInnerOrg);
        String content = createOrgChartContent(rootElement, showInnerOrg);
        content += createOrgChartHorizontalLine(rootElement);
        content += createInnerOrgChart(rootElement, innerOrgList);
        content = this.xmlHeader + content + this.xmlEnd;
        return FileUtil.createFile(content, absolutePath, "doc", FileUtil.ENCODE_UTF8);
    }

}
