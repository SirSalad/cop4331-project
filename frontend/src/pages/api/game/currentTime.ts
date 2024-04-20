import type { APIRoute } from "astro";
import { endpoint } from "./config";

export const GET: APIRoute = async (ctx) => {
    try {
        const response = await fetch(`${endpoint}/time`, { method: "GET", });
        const data = await response.json();

        return new Response(
            JSON.stringify(data),
            { status: 200 }
        );
    } catch (e) {
        return new Response(
            JSON.stringify({ message: "Error getting current time" }),
            { status: 500 }
        );
    }
};