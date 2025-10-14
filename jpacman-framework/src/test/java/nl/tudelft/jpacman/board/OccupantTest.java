package nl.tudelft.jpacman.board;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test suite to confirm that {@link Unit}s correctly (de)occupy squares.
 *
 * @author Jeroen Roosen 
 *
 */
class OccupantTest {

    /**
     * The unit under test.
     */
    private Unit unit;

    /**
     * Resets the unit under test.
     */
    @BeforeEach
    void setUp() {
        unit = new BasicUnit();
    }

    /**
     * Asserts that a unit has no square to start with.
     */
    @Test
    void noStartSquare() {
        unit = new BasicUnit();
        assertThat(unit.hasSquare()).isFalse();
    }

    /**
     * Tests that the unit indeed has the target square as its base after
     * occupation.
     */
    @Test
    void testOccupy() {
        //arrange
        unit = new BasicUnit();
        Square square = new BasicSquare();
        //act
        unit.occupy(square);
        //assert
        assertThat(square.equals(unit.getSquare())).isTrue();
    }

    /**
     * Test that the unit indeed has the target square as its base after
     * double occupation.
     */
    @Test
    void testReoccupy() {
        //arrange

        Square fstSquare = new BasicSquare();
        Square sndSquare = new BasicSquare();
        //act
        unit.occupy(fstSquare);
        unit.occupy(sndSquare);

        //Assert
        assertThat(fstSquare.equals(unit.getSquare())).isFalse();
        assertThat(sndSquare.equals(unit.getSquare())).isTrue();


    }
}
