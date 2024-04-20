import type { APIRoute } from "astro";
import { endpoint } from "./config";

export const POST: APIRoute = async (ctx) => {
    try {
        const body = await ctx.request.json();

        const response = await fetch(`${endpoint}/login`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ username: body.username, password: body.password }),
        });

        if (!response.ok || response.status !== 200) {
            return new Response(
                JSON.stringify({ message: "Invalid credentials" }),
                { status: 401 }
            );
        }

        const data = await response.text();
        console.log(data);
        ctx.cookies.set("token", data, {
            httpOnly: true,
            maxAge: 10 * 60,
            path: "/"
        })
        return new Response(
            JSON.stringify({ message: "Logged in" }),
            { status: 200 }
        );
        
    } catch (e) {
        console.log(e);
        return new Response(
            JSON.stringify({ message: "Error logging in" }),
            { status: 500 }
        );
    }
};