package org.ccci.gto.authorization.object;

import static org.ccci.gto.authorization.Constants.XMLNS_AUTHZ;

import java.util.regex.Pattern;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public final class User extends Entity {
    private static final Pattern userRegex = Pattern
            .compile("^[0-9A-F]{8}-[0-9A-F]{4}-[0-9A-F]{4}-[0-9A-F]{4}-[0-9A-F]{12}$");
    public static final User DEFAULT = new User("DEFAULT");
    public static final User GUEST = new User("GUEST");
    public static final User SUPERUSER = new User("SUPERUSER");

    public User(final Element xmlNode) {
        this(xmlNode.getAttributeNS(null, "name"));
    }

    public User(final String name) {
	super(Namespace.ROOT, name.toUpperCase());

	// throw an error if an invalid user is specified
        final String name2 = this.getName();
        if (!name2.equals("SUPERUSER") && !name2.equals("GUEST") && !name2.equals("DEFAULT")
                && !userRegex.matcher(name2).matches()) {
            throw new IllegalArgumentException("Invalid User Specified");
        }
    }

    /* (non-Javadoc)
     * @see org.ccci.gcx.authorization.object.Entity#toXml(org.w3c.dom.Document)
     */
    @Override
    public Element toXml(final Document doc) {
        final Element e = doc.createElementNS(XMLNS_AUTHZ, "user");
	e.setAttributeNS(null, "name", this.getName());
	return e;
    }
}
