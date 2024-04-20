import type { APIRoute } from "astro";
import { endpoint } from "./config";

export const POST: APIRoute = async (ctx) => {
    try {
        const body = await ctx.request.json();
        const response = await fetch(`${endpoint}/signup`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({ username: body.username, password: body.password }),
        });

        if (!response.ok || response.status !== 200) {
            return new Response(
                JSON.stringify({ message: `Internal server error: ${response.statusText}` }),
                { status: 401 }
            );
        }

        return new Response(
            JSON.stringify({ message: "Signed up" }),
            { status: 200 }
        );
    } catch (e) {
        return new Response(
            JSON.stringify({ message: "Error signing up" }),
            { status: 500 }
        );
    }
};