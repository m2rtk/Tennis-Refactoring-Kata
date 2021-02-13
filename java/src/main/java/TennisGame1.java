import java.util.Map;

public final class TennisGame1 implements TennisGame {

    private static final Map<Integer, String> SCORE_TO_TEXT = Map.of(
            0, "Love",
            1, "Fifteen",
            2, "Thirty",
            3, "Forty"
    );

    private final Player player1;
    private final Player player2;

    public TennisGame1(String player1Name, String player2Name) {
        this.player1 = new Player(player1Name);
        this.player2 = new Player(player2Name);
    }

    @Override
    public void wonPoint(String playerName) {
        if (playerName.equals(player1.name)) {
            player1.incrementScore();
        } else if (playerName.equals(player2.name)) {
            player2.incrementScore();
        } else {
            throw new IllegalArgumentException("Unknown player " + playerName);
        }
    }

    @Override
    public String getScore() {
        if (player1.scoreSameAs(player2)) {
            return evenScore();
        } else if (player1.hasTieBreakableScore() || player2.hasTieBreakableScore()) {
            return tieBreakScore();
        } else {
            return player1.scoreAsText() + "-" + player2.scoreAsText();
        }
    }

    private String evenScore() {
        if (player1.scoreAsNumber() >= 3) {
            return "Deuce";
        }

        return player1.scoreAsText() + "-All";
    }

    private String tieBreakScore() {
        int scoreDifference = player1.scoreAsNumber() - player2.scoreAsNumber();
        if (scoreDifference == 1) return "Advantage " + player1.name;
        else if (scoreDifference == -1) return "Advantage " + player2.name;
        else if (scoreDifference >= 2) return "Win for " + player1.name;
        else return "Win for " + player2.name;
    }

    private static class Player {
        private final String name;
        private int score;

        Player(String name) {
            this.name = name;
            this.score = 0;
        }

        void incrementScore() {
            this.score += 1;
        }

        String scoreAsText() {
            return SCORE_TO_TEXT.get(score);
        }

        int scoreAsNumber() {
            return score;
        }

        boolean hasTieBreakableScore() {
            return score >= 4;
        }

        boolean scoreSameAs(Player other) {
            return score == other.score;
        }
    }
}
