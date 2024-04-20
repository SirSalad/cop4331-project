import {
  Table,
  TableBody,
  TableCell,
  TableFooter,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import { useEffect, useState } from "react";
import { useAuth } from "./auth";

export function Leaderboard(token: string) {
  const [data, setData] = useState([]);
  const [yourPosition, setYourPosition] = useState("Loading...");
  const authData = useAuth(token);

  useEffect(() => {
    fetch("/api/leaderboard")
      .then((response) => response.json())
      .then((data) => {
        setData(data);
        setYourPosition(
          data.findIndex((user) => user["username"] === authData.username) + 1
        );
      });
  }, [token]);

  return (
    <Table>
      <TableHeader>
        <TableRow>
          <TableHead className="w-[100px]">Position</TableHead>
          <TableHead>Username</TableHead>
          <TableHead>Coins</TableHead>
          <TableHead>Premium</TableHead>
        </TableRow>
      </TableHeader>
      <TableBody>
        {data.map((user, idx) => (
          <TableRow key={idx + 1}>
            <TableCell>{idx + 1}</TableCell>
            <TableCell>{user["username"]}</TableCell>
            <TableCell>{user["coins"]}</TableCell>
            <TableCell>{user["isPremium"]}</TableCell>
          </TableRow>
        ))}
      </TableBody>
      <TableFooter>
        <TableRow>
          <TableCell colSpan={3}>Your position</TableCell>
          <TableCell className="text-right">{yourPosition}</TableCell>
        </TableRow>
      </TableFooter>
    </Table>
  );
}
