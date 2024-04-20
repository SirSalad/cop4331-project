import * as React from "react";

import { Button } from "@/components/ui/button";
import {
  Card,
  CardContent,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import { useAuth } from "./auth";
import { useEffect, useState, type FormEvent } from "react";

export function BettingView(token: string) {
  const authData = useAuth(token);
  const [amount, setAmount] = useState("");
  const [horse, setHorse] = useState("");
  const [horses, setHorses] = useState([]);

  useEffect(() => {
    fetch("/api/game/currentRace")
      .then((response) => response.json())
      .then((data) => {
        setHorses(data.horses);
      });
  }, []);

  const handleSubmit = (event: FormEvent) => {
    event.preventDefault();
    if (amount === "" || amount === "0" || isNaN(Number(amount)) || amount.startsWith("-")) {
      alert("Please enter a valid amount.");
      return;
    }

    if (horse === "") {
      alert("Please select a horse.");
      return;
    }

    if (Number(amount) > authData.coins) {
      alert("You don't have enough coins.");
      return;
    }

    fetch("/api/game/bet", { 
      method: "POST",
      body: JSON.stringify({ amount, horse }), 
    }).then((response) => {
      if (response.ok) {
        window.location.href = "/game/";
        return;
      }

      response.json().then((data) => {
        alert(data.message);
      });
    });
  };

  return (
    <Card className="w-[350px]">
      <CardHeader>
        <CardTitle>Place a bet</CardTitle>
        <CardDescription>Choose the horse and amount.</CardDescription>
      </CardHeader>
      <CardContent>
        <form>
          <div className="grid w-full items-center gap-4">
            <div className="flex flex-col space-y-1.5">
              <Label htmlFor="name">Amount</Label>
              <Input id="amount" placeholder="100" value={amount} onChange={(e) => setAmount(e.target.value)}/>
            </div>
            <div className="flex flex-col space-y-1.5">
              <Label htmlFor="horsek">Horse</Label>
              <Select>
                <SelectTrigger id="horse">
                  <SelectValue placeholder="Select" />
                </SelectTrigger>
                <SelectContent position="popper" onSelect={(e) => setHorse(e.target.value)}>
                  {horses.map((hors) => (
                    <SelectItem value={hors.name}>{hors.name}</SelectItem>
                  ))}
                </SelectContent>
              </Select>
            </div>
          </div>
        </form>
      </CardContent>
      <CardFooter className="flex justify-between">
        <Button variant="outline">
          <a href="/game/">Cancel</a>
        </Button>
        <Button id="submit" onClick={handleSubmit}>Bet</Button>
      </CardFooter>
    </Card>
  );
}
