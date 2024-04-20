import type { APIRoute } from "astro";
import { endpoint } from "./config";

export const POST: APIRoute = async (ctx) => {
    try {
        const token = ctx.cookies.get("token");
        if (!token) {
            return new Response(
                JSON.stringify({ message: "Invalid credentials" }),
                { status: 401 }
            );
        }

        const response = await fetch(`${endpoint}/refresh`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Bearer": token.value,
            }
        });
    
        if (!response.ok || response.status !== 200) {
            return new Response(
                JSON.stringify({ message: "Invalid credentials" }),
                { status: 401 }
            );
        }
    } catch (e) {
        return new Response(
            JSON.stringify({ message: "Error refreshing token" }),
            { status: 500 }
        );
    }
};