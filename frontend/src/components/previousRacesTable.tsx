import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import { useEffect, useState } from "react";
import { useAuth } from "./auth";

export function PreviousRacesTable(token: string) {
  const [data, setData] = useState<[string, unknown][]>([]);

  const authData = useAuth(token);

  useEffect(() => {
    fetch("/api/game/previousRaces")
      .then((response) => response.json())
      .then((data) => {
        setData(data);
      });
  }, []);

  return (
    <Card>
      <CardHeader>
        <CardTitle>Previous Races</CardTitle>
        <CardDescription>View results of the previous races.</CardDescription>
      </CardHeader>
      <CardContent>
        <Table>
          <TableHeader>
            <TableRow>
              <TableHead>Time</TableHead>
              <TableHead>Track</TableHead>
              <TableHead>Winner</TableHead>
            </TableRow>

            {Object.entries(data).map(([key, tab]) => (
              <TableRow key={key}>
                <TableCell className="font-medium">
                  {new Date(new Number(key)).toLocaleString()}
                </TableCell>
                <TableCell>{tab.track.track}</TableCell>
                <TableCell>{tab.winner.name}</TableCell>
              </TableRow>
            ))}
          </TableHeader>
          <TableBody></TableBody>
        </Table>
      </CardContent>
    </Card>
  );
}
