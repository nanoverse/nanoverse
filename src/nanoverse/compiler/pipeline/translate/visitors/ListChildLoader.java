package nanoverse.compiler.pipeline.translate.visitors;

import nanoverse.compiler.pipeline.interpret.nodes.ASTNode;
import nanoverse.compiler.pipeline.translate.helpers.TranslationCallback;
import nanoverse.compiler.pipeline.translate.nodes.*;
import nanoverse.compiler.pipeline.translate.symbol.*;
import org.slf4j.*;

/**
 * Created by dbborens on 9/22/2015.
 */
public class ListChildLoader {

    private final TranslationCallback walker;
    private final Logger logger;

    public ListChildLoader(TranslationCallback walker) {
        this.walker = walker;
        logger = LoggerFactory.getLogger(ListChildLoader.class);
    }

    public void loadChild(ASTNode child, ListSymbolTable symbolTable, ListObjectNode node) {
        // The child's identifier is an instantiable subclass of
        // the list class.
        String identifier = child.getIdentifier();

        InstantiableSymbolTable childIST =
            symbolTable.getSymbolTable(identifier);

        ObjectNode childNode = walker.walk(child, childIST);

        logger.debug("Loading new {} to list of {}",
            childNode.getInstantiatingClass().getSimpleName(),
            symbolTable.getBroadClass().getSimpleName());

        node.loadMember(childNode);
    }
}
