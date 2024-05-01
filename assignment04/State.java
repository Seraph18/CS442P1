package assignment04;

public enum State {
    WAITING {
        @Override
        public State enter_key(int a, GarageDoorOpener opener) {
            if (a == opener.getCode(0)) {
                opener.setValid(true);
            }
            return ONE_KEY;
        }
    }, ONE_KEY {
        @Override
        public State enter_key(int b, GarageDoorOpener opener) {
            if (b != opener.getCode(1)) {
                opener.setValid(false);
            }
            return TWO_KEY;
        }
    }, TWO_KEY {
        @Override
        public State enter_key(int c, GarageDoorOpener opener) {
            if (c != opener.getCode(2)) {
                opener.setValid(false);
            }
            return THREE_KEY;
        }
    }, THREE_KEY {
        @Override
        public State enter_key(int d, GarageDoorOpener opener) {
            if (d != opener.getCode(3)) {
                opener.setValid(false);
            }
            return FINAL;
        }
    }, FINAL {
        @Override
        public State enter_key(int k, GarageDoorOpener opener) {
            opener.setValid(false);
            return FINAL;
        }

        @Override
        public State press_open(GarageDoorOpener opener) {
            if (opener.isValid()) {
                opener.doorMotorOn();
            }
            opener.setValid(false);
            return WAITING;
        }
    };

    abstract State enter_key(int k, GarageDoorOpener opener);

    @Override
    public State press_open(GarageDoorOpener opener) {
        opener.setValid(false);
        return WAITING;
    }
}
