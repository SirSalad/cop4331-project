import type { APIRoute } from "astro";

export const POST: APIRoute = async (ctx) => {
    try {
        ctx.cookies.delete("token");

        return new Response(
            JSON.stringify({ message: "Logged out" }),
            { 
                status: 302,
                headers: {
                    "Location": "/"
                }
             }
        );
    } catch (e) {
        return new Response(
            JSON.stringify({ message: "Error logging out" }),
            { status: 500 }
        );
    }
}