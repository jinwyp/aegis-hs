import {Component, Input, Output, EventEmitter, OnChanges, SimpleChanges, ChangeDetectionStrategy} from '@angular/core'
import {NgbDate} from './ngb-date'
import {toInteger} from './util/util'
import {NgbDatepickerI18n} from './datepicker-i18n'
import {NgbCalendar} from './ngb-calendar'

@Component({
  selector: 'ngb-datepicker-navigation-select',
  changeDetection: ChangeDetectionStrategy.OnPush,
  styles: [`
    select {
        /* to align with btn-sm */
        padding: 0.25rem 0.5rem;
        font-size: 0.875rem;      
        line-height: 1.25;
        /* to cancel the custom height set by custom-select */
        height: inherit;
        width: 50%;
        background : none;
    }
  `],
  template: `
    <select
      [disabled]="disabled"
      class="custom-select d-inline-block"
      [value]="date?.month"
      (change)="changeMonth($event.target.value)"
      tabindex="-1">
        <option *ngFor="let m of months" [value]="m">{{ i18n.getMonthShortName(m) }}</option>
    </select><select
      [disabled]="disabled"
      class="custom-select d-inline-block"
      [value]="date?.year"
      (change)="changeYear($event.target.value)"
      tabindex="-1">
        <option *ngFor="let y of years" [value]="y">{{ y }}</option>
    </select> 
  `
})
export class NgbDatepickerNavigationSelect implements OnChanges {
  months: number[]
  years: number[] = []

  @Input() date: NgbDate
  @Input() disabled: boolean
  @Input() maxDate: NgbDate
  @Input() minDate: NgbDate

  @Output() select: any = new EventEmitter<NgbDate>()

  constructor(public i18n: NgbDatepickerI18n, private calendar: NgbCalendar) { this.months = calendar.getMonths() }

  ngOnChanges(changes: SimpleChanges) {
    if (changes['maxDate'] || changes['minDate'] || changes['date']) {
      this._generateYears()
      this._generateMonths()
    }
  }

  changeMonth(month: string) { this.select.emit(new NgbDate(this.date.year, toInteger(month), 1)) }

  changeYear(year: string) { this.select.emit(new NgbDate(toInteger(year), this.date.month, 1)) }

  private _generateMonths() {
    this.months = this.calendar.getMonths()

    if (this.date && this.date.year === this.minDate.year) {
        let index = -1

       this.months.forEach( (month, monthIndex) => {
           if (month === this.minDate.month && index === -1) {
               index = monthIndex
           }
       })



      this.months = this.months.slice(index)
    }

    if (this.date && this.date.year === this.maxDate.year) {

        let index2 = -1
      this.months.forEach( (month, monthIndex) => {
          if (month === this.maxDate.month && index2 === -1) {
              index2 = monthIndex
          }
      })


      this.months = this.months.slice(0, index2 + 1)
    }
  }

  private _generateYears() {
      this.years = []

      for (let i = 0; i < this.maxDate.year - this.minDate.year + 1; i++) {
          this.years.push (this.minDate.year + i)
      }

    // this.years = Array.from( {
    //     length: this.maxDate.year - this.minDate.year + 1
    // },  (e, i) => this.minDate.year + i)
  }
}
