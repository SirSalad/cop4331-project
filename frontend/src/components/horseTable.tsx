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

export function HorseTable() {
  const [horses, setHorses] = useState([]);

  useEffect(() => {
    fetch("/api/game/currentRace")
      .then((response) => response.json())
      .then((data) => {
        setHorses(data.horses);
      });
  }, []);

  return (
    <Card>
      <CardHeader>
        <CardTitle>Horses</CardTitle>
        <CardDescription>Look at all those horses.</CardDescription>
      </CardHeader>
      <CardContent>
        <Table>
          <TableHeader>
            <TableRow>
              <TableHead className="hidden w-[100px] sm:table-cell">
                <span className="sr-only">Image</span>
              </TableHead>
              <TableHead>Name</TableHead>
              <TableHead>Best Track</TableHead>
              <TableHead>Mane Style</TableHead>
              <TableHead>Breed</TableHead>
              <TableHead>Injuries</TableHead>
              <TableHead>Tail Length</TableHead>
            </TableRow>
          </TableHeader>
          <TableBody>
            {horses.map((horse) => (
              <TableRow key={horse.name}>
                <TableCell className="hidden sm:table-cell">
                  <img
                    alt="Product image"
                    className="aspect-square rounded-md object-cover"
                    height="64"
                    src="/img_avatar.png"
                    width="64"
                  />
                </TableCell>
                <TableCell className="font-medium">{horse.name}</TableCell>
                <TableCell>{horse.advTrack}</TableCell>
                <TableCell>{horse.maneStyle}</TableCell>
                <TableCell>{horse.race}</TableCell>
                <TableCell>{horse.injuries}</TableCell>
                <TableCell>{horse.tailLength}</TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </CardContent>
    </Card>
  );
}
