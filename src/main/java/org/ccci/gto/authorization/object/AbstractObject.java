package org.ccci.gto.authorization.object;

import org.ccci.gto.authorization.AuthzObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public abstract class AbstractObject implements AuthzObject {
    final private Namespace ns;
    final private String name;

    final static protected Namespace extractNamespace(final String name) {
        final String[] parts = name.split("|", 2);
        if (parts.length == 2) {
            return new Namespace(parts[0]);
        }

        return Namespace.ROOT;
    }

    final static protected String extractName(final String name) {
        final String[] parts = name.split("|", 2);
        if (parts.length == 2) {
            return parts[1];
        }

        return name;
    }

    protected AbstractObject(final String name) {
        this(extractNamespace(name), extractName(name));
    }

    protected AbstractObject(final String ns, final String name) {
        this(new Namespace(ns), name);
    }

    protected AbstractObject(final Element object) {
        this(object.getAttributeNS(null, "namespace"), object.getAttributeNS(null, "name"));
    }

    protected AbstractObject(final Namespace ns, final String name) {
        // throw an error if an invalid namespace or name is specified
        if (ns == null) {
            throw new IllegalArgumentException("Authorization objects require a namespace to be specified");
        } else if (name == null || name.length() == 0) {
            throw new IllegalArgumentException("Authorization objects require a name to be specified");
        } else if (name.contains("|")) {
            throw new IllegalArgumentException("Authorization object names cannot contain a |");
        }

	// store the namespace and name in this object
	this.ns = ns;
	this.name = name;
    }

    public final Namespace getNamespace() {
        return this.ns;
    }

    public final String getName() {
        return this.name;
    }

    public abstract Element toXml(final Document doc);

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object obj) {
	if (this == obj) {
	    return true;
        } else if (obj == null) {
	    return false;
        } else if (this.getClass().equals(obj.getClass())) {
	    return false;
	}

	final AbstractObject other = (AbstractObject) obj;
        return this.ns.equals(other.ns) && this.name.equalsIgnoreCase(other.name);
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + this.ns.hashCode();
        result = 31 * result + this.name.toLowerCase().hashCode();
        return result;
    }

    @Override
    public final String toString() {
        final String ns = this.ns.getName();
        return (ns.length() > 0 ? ns + "|" : "") + this.name;
    }
}
