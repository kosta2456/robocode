package edu.ufl.cise.cs1.robots;

import robocode.*;
import robocode.util.Utils;

import java.awt.*;

public class Sunset extends TeamRobot {

    static int distance = 125;
    static int moveDirection;
    static int reverseDirection = -1;

    public void run() {

        setAllColors(Color.PINK);
        setAdjustRadarForRobotTurn(true);
        setAdjustGunForRobotTurn(true);
        setAdjustRadarForGunTurn(true);

        while(true) {

            ahead(distance);
            setTurnRadarRightRadians( Double.POSITIVE_INFINITY );
            ahead(distance * reverseDirection);

        }

    }

    public void onScannedRobot(ScannedRobotEvent e) {

        if (isTeammate(e.getName())) {
            return;
        }


        //One on One Radar Width Lock Inspired By http://robowiki.net/wiki/One_on_One_Radar
        double angleToEnemy = getHeadingRadians() + e.getBearingRadians();
        double radarTurn = Utils.normalRelativeAngle(angleToEnemy - getRadarHeadingRadians());
        double extraTurn = Math.min(Math.atan( 36.0 / e.getDistance() ), Rules.RADAR_TURN_RATE_RADIANS );

        if (radarTurn < 0)
            radarTurn -= extraTurn;
        else
            radarTurn += extraTurn;

        setTurnRadarRightRadians(radarTurn);


        //Movement inspired by http://mark.random-article.com/weber/java/robocode/lesson5.html
        setTurnRight(e.getBearing() + 90);

        if (getTime() % 20 == 0) {
            moveDirection *= -1;
            setAhead(distance * moveDirection);
        }

        setTurnGunRightRadians(Utils.normalRelativeAngle(angleToEnemy - getGunHeadingRadians() + (e.getVelocity() * Math.sin(e.getHeadingRadians() - angleToEnemy) / 13.0)));

        setFire(3);
    }

    public void onHitRobot(HitRobotEvent e){
        ahead(moveDirection * reverseDirection);
    }

    public void onHitByBullet(HitByBulletEvent e) {
        ahead(30 * reverseDirection);
    }

    public void onHitWall(HitWallEvent e) {

        turnRadarRight(360);
        setTurnRight(90);
        ahead(200 * reverseDirection);

    }

}