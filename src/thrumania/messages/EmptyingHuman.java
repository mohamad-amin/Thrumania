package thrumania.messages;

import thrumania.board.item.GameItems.ships.ContainerShip;
import thrumania.utils.Coordinate;

import java.awt.*;
import java.awt.event.ComponentEvent;

/**
 * Created by sina on 7/3/16.
 */
public class EmptyingHuman extends ComponentEvent {
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
    private ContainerShip containerShip;
    private  Coordinate endCoord ;
    public EmptyingHuman(Component source, ContainerShip containerShip  , Coordinate endCord) {
        super(source, Messages.EMPTING_HUMAN);
        this.containerShip = containerShip;
        this.endCoord = endCord;
    }

    public ContainerShip getContainerShip() {
        return containerShip;
    }

    public Coordinate getEndCoord() {
        return endCoord;
    }
}
