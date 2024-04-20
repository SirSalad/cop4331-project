import type { APIRoute } from "astro";
import { endpoint } from "./config";

export const GET: APIRoute = async (ctx) => {
    try {
        const response = await fetch(`${endpoint}/currentgame`, { method: "GET" });
        const body = await response.json();

        return new Response(
            JSON.stringify(body),
            { status: 200 }
        );
    } catch (e) {
        return new Response(
            JSON.stringify({ message: "Error getting current game" }),
            { status: 500 }
        );
    }
};