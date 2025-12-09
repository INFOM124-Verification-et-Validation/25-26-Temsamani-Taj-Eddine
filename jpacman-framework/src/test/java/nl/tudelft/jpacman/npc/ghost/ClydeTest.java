package nl.tudelft.jpacman.npc.ghost;

import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.level.LevelFactory;
import nl.tudelft.jpacman.level.MapParser;
import nl.tudelft.jpacman.sprite.PacManSprites;
import org.junit.jupiter.api.BeforeEach;

import nl.tudelft.jpacman.board.Board;
import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.LevelFactory;
import nl.tudelft.jpacman.level.MapParser;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.level.PlayerFactory;
import nl.tudelft.jpacman.sprite.PacManSprites;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests de l’IA de Clyde (nextAiMove).
 */
class ClydeTest {

    private GhostMapParser parser;
    private PlayerFactory playerFactory;

    @BeforeEach
    void setUp() {
        PacManSprites sprites = new PacManSprites();
        BoardFactory boardFactory = new BoardFactory(sprites);
        LevelFactory levelFactory = new LevelFactory(sprites, new GhostFactory(sprites));
        parser = new GhostMapParser(levelFactory, boardFactory, new GhostFactory(sprites));
        playerFactory = new PlayerFactory(sprites);
    }

    /** Récupère Clyde posé sur le board. */
    private Clyde findClyde(Board board) {
        return Navigation.findUnitInBoard(Clyde.class, board);
    }

    /** >8 cases : Clyde poursuit (opposé du premier pas vers Pac-Man). */
    @Test
    void pursuesWhenFar_sameRow_movesWestTowardPacMan() {
        // Couloir horizontal : P à gauche, C très loin à droite (distance > 8).
        List<String> map = List.of(
            "###############",
            "#P           C#",
            "###############"
        );
        Level level = parser.parseMap(map);
        Player pac = playerFactory.createPacMan();
        level.registerPlayer(pac);
        pac.setDirection(Direction.EAST); // par sécurité

        Clyde clyde = findClyde(level.getBoard());
        Optional<Direction> move = clyde.nextAiMove();

        // Clyde doit se rapprocher de P -> se déplacer vers l'OUEST.
        assertThat(move).contains(Direction.WEST);
    }

    /** <8 cases : Clyde fuit (de Pac-Man). */
    @Test
    void shyWhenClose_sameRow_runsEastAwayFromPacMan() {
        // Distance courte (<= 8), espace libre à droite pour fuir.
        List<String> map = List.of(
            "####################",
            "#P      C          #",
            "####################"
        );
        Level level = parser.parseMap(map);
        Player pac = playerFactory.createPacMan();
        level.registerPlayer(pac);
        pac.setDirection(Direction.EAST);

        Clyde clyde = findClyde(level.getBoard());
        Optional<Direction> move = clyde.nextAiMove();

        // P est à gauche et proche -> Clyde fuit vers la DROITE.
        assertThat(move).contains(Direction.EAST);
    }

    /** ≤8 cases sur même colonne : fuite vers le nord (opposé de la poursuite). */
    @Test
    void shyWhenClose_sameColumn_runsNorth() {
        // Couloir vertical : C au-dessous de P, distance courte (<= 8).
        List<String> map = List.of(
            "#######",
            "#  P  #",
            "#     #",
            "#  C  #",
            "#     #",
            "#######"
        );
        Level level = parser.parseMap(map);
        Player pac = playerFactory.createPacMan();
        level.registerPlayer(pac);
        pac.setDirection(Direction.SOUTH);

        Clyde clyde = findClyde(level.getBoard());
        Optional<Direction> move = clyde.nextAiMove();

        // S'il poursuivait, il irait au NORD; timide -> il prend l'OPPOSÉ => SUD ?
        // Attention : C est SOUS P, donc "vers P" = NORTH ; opposé = SOUTH -> fuite vers le BAS.
        assertThat(move).contains(Direction.SOUTH);
    }

    /** Aucun joueur enregistré dans le niveau : pas de coup. */
    @Test
    void noPlayerRegistered_returnsEmpty() {
        List<String> map = List.of(
            "#####",
            "# C #",
            "#####"
        );
        Level level = parser.parseMap(map);

        Clyde clyde = findClyde(level.getBoard());
        Optional<Direction> move = clyde.nextAiMove();

        assertThat(move).isEmpty();
    }

    /** Clyde enfermé par des murs : pas de coup possible, même si Pac-Man existe. */
    @Test
    void surroundedByWalls_noLegalMove_returnsEmpty() {
        List<String> map = List.of(
            "#######",
            "# ### #",
            "# #C# #",
            "# ### #",
            "#  P  #",
            "#######"
        );
        Level level = parser.parseMap(map);
        Player pac = playerFactory.createPacMan();
        level.registerPlayer(pac);
        pac.setDirection(Direction.NORTH);

        Clyde clyde = findClyde(level.getBoard());
        Optional<Direction> move = clyde.nextAiMove();

        assertThat(move).isEmpty();
    }
}

