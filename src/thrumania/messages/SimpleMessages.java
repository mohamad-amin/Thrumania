package thrumania.messages;

import java.awt.*;
import java.awt.event.ComponentEvent;

/**
 * Created by sina on 6/1/16.
 */
public class SimpleMessages extends ComponentEvent {
    /**
     * Constructs a <code>ComponentEvent</code> object.
     * <p> This method throws an
     * <code>IllegalArgumentException</code> if <code>source</code>
     * is <code>null</code>.
     *
     * @param source The <code>Component</code> that originated the event
     * @param id     An integer indicating the type of event.
     *               For information on allowable values, see
     *               the class description for {@link ComponentEvent}
     * @throws IllegalArgumentException if <code>source</code> is null
     * @see #getComponent()
     * @see #getID()
     */
    public SimpleMessages(Component source, int id) {
        super(source, id);
    }
}
