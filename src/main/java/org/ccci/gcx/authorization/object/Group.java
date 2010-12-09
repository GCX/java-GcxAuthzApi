/**
 *
 */
package org.ccci.gcx.authorization.object;

import org.ccci.gcx.authorization.AuthzConstants;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author frett
 *
 */
public final class Group extends Entity {
    public Group(final Namespace ns, final String name) {
	super(ns, name);
    }

    public Group(final String ns, final String name) {
	this(new Namespace(ns), name);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.ccci.gcx.authorization.object.Entity#toXml(org.w3c.dom.Document)
     */
    @Override
    public Element toXml(final Document doc) {
	final Element e = doc.createElementNS(AuthzConstants.XMLNS, "group");
	e.setAttributeNS(null, "namespace", this.getNamespace().toString());
	e.setAttributeNS(null, "name", this.getName());
	return e;
    }
}
