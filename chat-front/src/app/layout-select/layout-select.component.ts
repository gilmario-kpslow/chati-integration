import { CommonModule } from '@angular/common';
import {
  Component,
  ElementRef,
  Host,
  Input,
  Optional,
  Renderer2,
  SkipSelf,
  forwardRef,
} from '@angular/core';
import {
  AbstractControl,
  ControlContainer,
  FormControl,
  NG_VALUE_ACCESSOR,
  ReactiveFormsModule,
} from '@angular/forms';
import { ErroPipe } from '../layout-input/pipe-error';

@Component({
  selector: 'app-select',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule, ErroPipe],
  templateUrl: './layout-select.component.html',
  styleUrl: './layout-select.component.css',
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => LayoutSelectComponent),
      multi: true,
    },
  ],
})
export class LayoutSelectComponent {
  @Input() options: any[] = [];
  @Input() label: string = 'Campo';
  disable = false;
  @Input() formControlName: string | undefined;
  private control: AbstractControl | undefined | null;
  @Input() type: string = 'text';

  onChange = (_: string) => {};
  onTouch = () => {};

  constructor(
    private renderer: Renderer2,
    private elementRef: ElementRef,
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
