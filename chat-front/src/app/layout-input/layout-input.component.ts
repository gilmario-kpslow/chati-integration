import {
  Component,
  Host,
  Input,
  OnInit,
  Optional,
  SkipSelf,
  forwardRef,
} from '@angular/core';
import {
  AbstractControl,
  ControlContainer,
  ControlValueAccessor,
  FormControl,
  NG_VALUE_ACCESSOR,
  ReactiveFormsModule,
} from '@angular/forms';
import { ErroPipe } from './pipe-error';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-input',
  standalone: true,
  imports: [ReactiveFormsModule, ErroPipe, CommonModule],
  templateUrl: './layout-input.component.html',
  styleUrls: ['./layout-input.component.css'],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => LayoutInputComponent),
      multi: true,
    },
  ],
})
export class LayoutInputComponent implements ControlValueAccessor, OnInit {
  @Input() label: string = 'Campo';
  disable = false;
  @Input() formControlName: string | undefined;
  private control: AbstractControl | undefined | null;
  @Input() type: string = 'text';
  @Input() placeholder: string = '';

  onChange = (_: string) => {};
  onTouch = () => {};

  constructor(
    @Host() @SkipSelf() @Optional() private controlContainer: ControlContainer
  ) {}

  public writeValue(inputValue: string): void {}

  public registerOnChange(fn: any): void {
    this.onChange = fn;
  }

  public registerOnTouched(fn: any): void {
    this.onTouch = fn;
  }

  public setDisabledState(isDisabled: boolean): void {
    this.disable = isDisabled;
  }

  hasError(): boolean | undefined | null {
    return this.control && this.control.invalid && this.control.touched;
  }

  get errorMessage() {
    return this.control?.errors;
  }

  ngOnInit(): void {
    if (
      this.formControlName &&
      this.controlContainer &&
      this.controlContainer.control
    ) {
      this.control = this.controlContainer.control.get(this.formControlName);
    }
  }

  get form(): FormControl {
    return this.control as FormControl;
  }
}
