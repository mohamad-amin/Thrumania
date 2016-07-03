package thrumania.messages;

import thrumania.board.item.GameItems.people.Human;
import thrumania.board.item.GameItems.ships.ContainerShip;

import java.awt.*;
import java.awt.event.ComponentEvent;

/**
 * Created by sina on 7/3/16.
 */
public class PickingHumanUp extends ComponentEvent {
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

    ContainerShip containerShip;
    Human human;
    public PickingHumanUp(Component source, ContainerShip containerShip , Human human) {
        super(source,  Messages.PICKING_HUMAN_UP) ;
        this.containerShip = containerShip;
        this.human = human;
    }

    public ContainerShip getContainerShip() {
        return containerShip;
    }

    public Human getHuman() {
        return human;
    }
}
