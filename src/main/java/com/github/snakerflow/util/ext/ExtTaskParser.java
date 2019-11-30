package com.github.snakerflow.util.ext;


import org.snaker.engine.model.NodeModel;
import org.snaker.engine.parser.impl.TaskParser;
import org.w3c.dom.Element;

/**
 * 自定义任务解析器
 * @author yuqs
 * @since 0.1
 */
public class ExtTaskParser extends TaskParser {
    private static final String ATTR_ASSIGNEEDISPLAY = "assigneeDisplay";
    @Override
    protected void parseNode(NodeModel node, Element element) {
        super.parseNode(node, element);
        ExtTaskModel task = (ExtTaskModel)node;
        task.setAssigneeDisplay(element.getAttribute(ATTR_ASSIGNEEDISPLAY));
    }

    @Override
    protected NodeModel newModel() {
        return new ExtTaskModel();
    }
}
