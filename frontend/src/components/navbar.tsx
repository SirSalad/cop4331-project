import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu";
import { CircleUser } from "lucide-react";
import { Button } from "./ui/button";
import { useAuth } from "./auth";
import ReactModal from "react-modal";

export function Navbar(token: string) {
  const authData = useAuth(token);

  return (
    <div className="flex w-full flex-col">
      <header className="sticky top-0 flex h-16 items-center gap-4 border-b bg-background px-4 md:px-6 justify-between">
        <nav className="hidden flex-col gap-6 text-lg font-medium md:flex md:flex-row md:items-center md:gap-5 md:text-sm lg:gap-6">
          <a
            href="/"
            className="flex items-center gap-2 text-lg font-semibold md:text-base"
          >
            <img src="/favicon.png" style={{ width: 40, height: 40 }}></img>
            <span className="sr-only"></span>
          </a>
          <a
            href="/game"
            className="text-foreground transition-colors hover:text-foreground"
          >
            Game
          </a>
          <a
            href="/leaderboard"
            className="text-foreground transition-colors hover:text-foreground"
          >
            Leaderboard
          </a>
        </nav>
        <p className="ml-auto">Coins: {authData.coins}🪙</p>
        <DropdownMenu>
          <DropdownMenuTrigger asChild>
            <Button variant="secondary" size="icon" className="rounded-full">
              <CircleUser className="h-5 w-5" />
              <span className="sr-only">Toggle user menu</span>
            </Button>
          </DropdownMenuTrigger>
          <DropdownMenuContent align="end">
            <DropdownMenuItem
              onClick={(e) => {
                e.preventDefault();
                UpgradeModal();
              }}
            >
              Upgrade
            </DropdownMenuItem>

            <DropdownMenuItem
              onClick={(e) => {
                e.preventDefault();
                window.location.href = "/";
                fetch("/api/auth/logout", { method: "POST" });
              }}
            >
              Logout
            </DropdownMenuItem>
          </DropdownMenuContent>
        </DropdownMenu>
      </header>
    </div>
  );
}

function UpgradeModal() {
  return (
    <ReactModal isOpen={true}>
      <div className="fixed inset-0 z-50 flex items-center justify-center bg-black bg-opacity-50">
        <div className="bg-background rounded-lg p-6 w-full max-w-md">
          <h2 className="text-xl font-semibold">Upgrade to Premium</h2>
          <p className="text-sm text-muted-foreground mt-2">
            Get access to exclusive features.
          </p>
          <div className="mt-4">
            <Button>Upgrade</Button>
          </div>
        </div>
      </div>
    </ReactModal>
  );
}
