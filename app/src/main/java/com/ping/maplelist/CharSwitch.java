package com.ping.maplelist;

public class CharSwitch {

    int Select = 0;
    int Destroy = 0;

    public void CharSwitch(int addr)
    {
        switch (addr)
        {
            case (R.id.ImgChoose1):
                this.Select = 0;
                break;

            case (R.id.ImgChoose2):
                this.Select = 1;
                break;

            case (R.id.ImgChoose3):
                this.Select = 2;
                break;

            case (R.id.ImgChoose4):
                this.Select = 3;
                break;

            case (R.id.ImgChoose5):
                this.Select = 4;
                break;

            case (R.id.ImgChoose6):
                this.Select = 5;
                break;

            case (R.id.ImgChoose7):
                this.Select = 6;
                break;

            case (R.id.ImgChoose8):
                this.Select = 7;
                break;

            default:
                this.Select = 0;
                break;
        }
    }

    public void CharDestroy(int addr)
    {
        switch (addr)
        {
            case (R.id.ImgDestroy1):
                this.Destroy = 0;
                break;

            case (R.id.ImgDestroy2):
                this.Destroy = 1;
                break;

            case (R.id.ImgDestroy3):
                this.Destroy = 2;
                break;

            case (R.id.ImgDestroy4):
                this.Destroy = 3;
                break;

            case (R.id.ImgDestroy5):
                this.Destroy = 4;
                break;

            case (R.id.ImgDestroy6):
                this.Destroy = 5;
                break;

            case (R.id.ImgDestroy7):
                this.Destroy = 6;
                break;

            case (R.id.ImgDestroy8):
                this.Destroy = 7;
                break;

            default:
                this.Destroy = 0;
                break;
        }
    }

}


