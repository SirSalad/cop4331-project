import { useEffect, useState } from "react";

const weatherEmojis: { [key: string]: string } = {
    dry: "☀️",
    wet: "🌧️",
    rainy: "🌧️",
    cloudy: "☁️",
    snowy: "❄️"
};

export function TrackInfo() {
    const [trackData, setTrackData] = useState({ track: "", weather: "" });

    useEffect(() => {
        fetch("/api/game/currentRace")
            .then((response) => response.json())
            .then((data) => {
                setTrackData(data.track);
            });
    }, []);

    return (
        <div>
            <h1 className="scroll-m-20 text-4xl font-extrabold tracking-tight lg:text-5xl">The track is: {trackData.track}</h1>
            <h1 className="scroll-m-20 text-4xl font-extrabold tracking-tight lg:text-5xl">The weather is: {trackData.weather} {weatherEmojis[trackData.weather]}</h1>
        </div>
    );
}