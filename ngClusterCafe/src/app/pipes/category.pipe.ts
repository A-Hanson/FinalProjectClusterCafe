import { Pipe, PipeTransform } from '@angular/core';
import { Category } from '../models/category';

@Pipe({
  name: 'category'
})
export class CategoryPipe implements PipeTransform {

  transform(value: any[], category: Category): any[] {
    let filtered: any[] = [];
    if (category === null) {
      filtered = value;
    } else {
      value.forEach( (item) => {
        if (item.category === category) {
          filtered.push(item);
        }
      });
    }
    return filtered;
  }

}
