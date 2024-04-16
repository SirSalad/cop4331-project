const endpoint = "http://localhost:8081/game";

export const getGameTime = async (): Promise<string> => {
  const response = await fetch(`${endpoint}/time`);
  const time = await response.json();
  return formatTime(time);
};

export const getCurrentGame = async (): Promise<any> => {
  const response = await fetch(`${endpoint}/currentgame`);
  const game = await response.json();
  return game;
};


export const getPreviousGames = async (): Promise<string> => {
    const response = await fetch(`${endpoint}/previousgames`);
    const game = await response.json();
    return game;
};

function formatTime(milliseconds: number): string {
    const totalSeconds = Math.floor(milliseconds / 1000);

    const minutes = Math.floor(totalSeconds / 60);
    const seconds = totalSeconds % 60;

    const formattedMinutes = String(minutes).padStart(2, '0');
    const formattedSeconds = String(seconds).padStart(2, '0');

    return `${formattedMinutes}:${formattedSeconds}`;
}