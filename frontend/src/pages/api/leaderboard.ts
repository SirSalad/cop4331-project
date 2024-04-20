import type { APIRoute } from "astro";

export const endpoint = "http://localhost:8080/leaderboard";

export const GET: APIRoute = async (ctx) => {
    try {
        const response = await fetch(endpoint, { method: "GET" });

        if (!response.ok || response.status !== 200) {
            return new Response(
                JSON.stringify({ message: `Internal server error: ${response.statusText}` }),
                { status: 500 }
            );
        }

        return new Response(
            response.body,
            { 
                status: 200 
            }
        );
    } catch (e) {
        console.log(e);
        return new Response(
            JSON.stringify({ message: "Error getting leaderboard" }),
            { status: 500 }
        );
    }
};