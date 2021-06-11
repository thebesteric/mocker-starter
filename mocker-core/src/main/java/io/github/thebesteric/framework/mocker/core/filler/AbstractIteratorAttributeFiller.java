package io.github.thebesteric.framework.mocker.core.filler;

import java.util.ArrayList;
import java.util.Collection;

/**
 * AbstractIteratorAttributeFiller
 *
 * @author Eric Joe
 * @version 1.0
 * @date 2021-06-10 18:49
 * @since 1.0
 */
public abstract class AbstractIteratorAttributeFiller extends AbstractAttributeFiller {

    @Override
    protected Collection<AttributeFiller> getAttributeFillers() {
        Collection<AttributeFiller> attributeFillers = new ArrayList<>(2);
        attributeFillers.add(applicationContext.getBean(StringAttributeFiller.class));
        attributeFillers.add(applicationContext.getBean(WarpAttributeFiller.class));
        attributeFillers.add(applicationContext.getBean(ComplexAttributeFiller.class));
        return attributeFillers;
    }

}
