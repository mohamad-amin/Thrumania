package thrumania.messages;

import thrumania.board.item.GameItems.ships.Ships;

import java.awt.*;
import java.awt.event.ComponentEvent;

/**
 * Created by sina on 7/5/16.
 */
public class RemovingShipsFromPanel extends ComponentEvent {
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
    private Ships ships ;
    public RemovingShipsFromPanel(Component source, Ships ships) {
        super(source, Messages.REMOVING_SHIP_FROM_PANEL);
        this.ships = ships;
    }

    public Ships getShips() {
        return ships;
    }
}
