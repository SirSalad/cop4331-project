import type { APIRoute } from "astro";
import { endpoint } from "./config";

export const POST: APIRoute = async (ctx) => {
    try {
        const response = await fetch(`${endpoint}/bet`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + ctx.cookies.get("token")?.value
            },
            body: JSON.stringify({
                amount: ctx.request.amount,
                horse: ctx.request.horse
            })
        });

        return new Response(
            JSON.stringify({ message: "Bet placed" }),
            { status: 200 }
        );
    } catch (e) {
        return new Response(
            JSON.stringify({ message: "Error betting" }),
            { status: 500 }
        );
    }
};