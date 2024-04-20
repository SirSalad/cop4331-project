import { useEffect, useState } from "react";
import { Tabs, TabsList, TabsTrigger } from "./ui/tabs";

interface GameViewSelectorProps {
  value: string;
}

const tabs = {
  horses: { name: "Horses", link: "/game/" },
  track: { name: "Track Info", link: "/game/track/" },
  bet: { name: "Bet", link: "/game/bet/" },
  previousRaces: { name: "Previous Races", link: "/game/previousRaces" },
};

export function GameViewSelector({ value }: GameViewSelectorProps) {
  const [time, setTime] = useState("Loading...");
  useEffect(() => {
    const taskId = setInterval(() => {
      fetch("/api/game/currentTime", { method: "GET" })
        .then((response) => response.json())
        .then((data) => {
          setTime(durationMsTommss(Number(data.time)));
        });
    }, 1000);

    return () => clearInterval(taskId)
  }, []);

  return (
    <Tabs defaultValue={value}>
      <TabsList>
        {Object.entries(tabs).map(([key, tab]) => (
          <TabsTrigger key={key} value={key}>
            <a href={tab.link}>{tab.name}</a>
          </TabsTrigger>
        ))}
        <TabsTrigger key="time" value="time">
          Time until next race: {time}
        </TabsTrigger>
      </TabsList>
    </Tabs>
  );
}

function durationMsTommss(duration: number) {
  const minutes = Math.floor(duration / 60000);
  const seconds = ((duration % 60000) / 1000).toFixed(0);
  return minutes + ":" + (parseInt(seconds) < 10 ? "0" : "") + seconds;
}
