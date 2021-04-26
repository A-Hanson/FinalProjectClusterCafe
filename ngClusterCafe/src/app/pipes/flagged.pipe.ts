import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'flagged'
})
export class FlaggedPipe implements PipeTransform {

  transform(value: any[], role: string): any[] {
    let filtered: any[] = [];
    if (role === 'admin') {
      filtered = value;
    } else {
      value.forEach( (item) => {
        if (!item.flagged) {
          filtered.push(item);
        }
      });
    }
    return filtered;
  }

}
