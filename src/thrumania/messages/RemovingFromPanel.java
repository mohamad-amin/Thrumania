package thrumania.messages;

import thrumania.board.item.GameItems.people.Human;

import java.awt.*;
import java.awt.event.ComponentEvent;

/**
 * Created by sina on 7/4/16.
 */
public class RemovingFromPanel extends ComponentEvent {
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
    private Human human;
    public RemovingFromPanel(Component source, Human human) {

        super(source, Messages.REMOVING_FROM_PANEL);
        this.human = human;
    }

    public Human getHuman() {
        return human;
    }
}
