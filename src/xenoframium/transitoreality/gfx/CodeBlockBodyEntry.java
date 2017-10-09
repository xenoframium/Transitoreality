package xenoframium.transitoreality.gfx;

import xenoframium.ecs.Entity;

/**
 * Created by chrisjung on 9/10/17.
 */
public class CodeBlockBodyEntry {
    final CodeBlockBodyEntryType type;
    final Entity codeBlockBody;
    final String tag;
    final String defaultText;

    public CodeBlockBodyEntry(String defaultText) {
        this.type = CodeBlockBodyEntryType.TEXT_BOX;
        this.tag = null;
        this.defaultText = defaultText;
        this.codeBlockBody = null;
    }

    public CodeBlockBodyEntry(String defaultText, String tag) {
        this.type = CodeBlockBodyEntryType.TEXT_FIELD;
        this.tag = tag;
        this.defaultText = defaultText;
        this.codeBlockBody = null;
    }

    public CodeBlockBodyEntry(Entity codeBlockBody) {
        this.type = CodeBlockBodyEntryType.CODE_BLOCK;
        this.tag = null;
        this.defaultText = null;
        this.codeBlockBody = codeBlockBody;
    }
}
