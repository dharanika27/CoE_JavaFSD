import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'highlightChampion'
})
export class HighlightChampionPipe implements PipeTransform {

  transform(team: string, titles: number): string {
    if (titles > 0) {
      return `🏆 ${team} (${titles} titles)`;
    } else {
      return team;
    }
  }

}
