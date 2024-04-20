import { defineMiddleware } from "astro:middleware";

const whitelist = ["/", "/signup", "/api/auth/signup", "/api/auth/login"];

export const onRequest = defineMiddleware(async (context, next) => {
  if (whitelist.includes(context.url.pathname)) return next();

  const token = context.cookies.get("token");
  if (!token) {
    return new Response(JSON.stringify({ message: "Unauthorized" }), {
      status: 302,
      headers: {
        Location: "/",
      },
    });
  }

  return next();
});
