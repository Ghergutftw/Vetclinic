import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'sortByStar',
  pure: false,
})
export class StarSortPipe implements PipeTransform {
  transform(array: any[]): any[] {
    if (!Array.isArray(array)) {
      return array;
    }

    return array.sort((a, b) => (b.starred ? 1 : 0) - (a.starred ? 1 : 0));
  }
}
